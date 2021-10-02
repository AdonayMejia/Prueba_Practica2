package com.example.practica2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practica2.databinding.ActivityMainBinding
import entities.Product
import repository.ProductRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buildList()
        addListener()
    }

    private fun buildList() {
        val repository = ProductRepository.getRepository(this)
        val layoutManager= GridLayoutManager(this, 1)

        lifecycleScope.launch {
            repository.allProduct.collect { products ->
                binding.rvProducts.apply {
                    adapter = ProductAdapter(products, this@MainActivity)
                    setLayoutManager(layoutManager)
                }
                total(products)
            }
        }
    }

    private fun addListener() {
        binding.fbAdd.setOnClickListener {
            startActivity(Intent(this, addProductActivity::class.java))
        }

    }
    private fun total(list : List<Product>){
        var precio_total =0.0

        for (j in 0 until list.size){
            precio_total += list[j].precio.toDouble()
        }
        binding.tvText.setText("$" + precio_total.toString())
    }
}