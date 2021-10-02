package com.example.practica2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.example.practica2.databinding.ActivityAddProductBinding
import entities.Product
import repository.ProductRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class addProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListener()
    }
    private fun addListener(){
        val repository= ProductRepository.getRepository(this)

        binding.btnAdd.setOnClickListener {
            hideKeyboard()
            with(binding){
                if (etCantidad.text.isBlank() || etName.text.isBlank() || etPrecio.text.isBlank()) {
                    Snackbar.make(this.root, "Complete todos los Campos", Snackbar.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            repository.insert(
                                Product(
                                    nombre = etName.text.toString(),
                                    precio = etPrecio.text.toString().toDouble(),
                                    cantidad = etCantidad.text.toString().toInt()

                                )
                            )
                        }
                        onBackPressed()
                    }
                }
            }
        }
    }
    private fun hideKeyboard() {
        val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}