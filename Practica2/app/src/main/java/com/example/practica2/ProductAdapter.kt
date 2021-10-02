package com.example.practica2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2.databinding.ItemProductBinding
import entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.ProductRepository

@Suppress("MemberVisibilityCanBePrivate")
class ProductAdapter (private val list: List<Product>, private val context: Context):
    RecyclerView.Adapter<ProductAdapter.ProductsViewHolder>() {

        class ProductsViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder{
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)



    }
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        with(holder.binding) {
            tvCantidad.text ="X" + list[position].cantidad.toString()
            tvNombre.text = list[position].nombre
            tvPrecio.text = "$" + list[position].precio.toString()

            cleanProduct.setOnClickListener {
                val repository= ProductRepository.getRepository(context)
                CoroutineScope(Dispatchers.IO).launch {
                    repository.deleteOneItem(list[position].id)
                }
            }
        }
    }



    override fun getItemCount(): Int = list.size

}