package id.wendei.store.data.remote.api

import id.wendei.store.data.remote.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("products/{id}")
    suspend fun getDetailProduct(@Path("id") idProduct: Int): Response<ProductResponse>

    @GET("products/categories")
    suspend fun getProductCategories(): Response<List<String>>

    @GET("products/category/{category}")
    suspend fun getProductByCategory(@Path("category") category: String): Response<List<ProductResponse>>

}