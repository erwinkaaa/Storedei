package id.wendei.store.domain.repository

import id.wendei.store.domain.model.Category
import id.wendei.store.domain.model.Product
import id.wendei.store.data.remote.util.Resource
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getDetailProduct(id: Int): Flow<Resource<Product>>
    suspend fun getProductCategories(): Flow<Resource<MutableList<Category>>>
    suspend fun getProductByCategory(category: String): Flow<Resource<List<Product>>>
    suspend fun getCart(): Flow<List<Product>>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(product: Product)
}