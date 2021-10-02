package repository

import android.content.Context
import dao.ProductDao
import database.ProductRoomDatabase
import entities.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository (private val productDao: ProductDao) {

    companion object {
        private var INSTANCE: ProductRepository? = null

        fun getRepository(context: Context): ProductRepository {
            return INSTANCE ?: synchronized(this) {
                val database = ProductRoomDatabase.getDatabase(context)
                val instance = ProductRepository(database.productDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val allProduct: Flow<List<Product>> = productDao.getAlphabetizedProducts()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
    suspend fun deleteOneItem(id: Int) {
        productDao.deleteOneItem(id)
    }

}