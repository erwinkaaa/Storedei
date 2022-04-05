package id.wendei.store.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wendei.store.base.BaseViewModel
import id.wendei.store.data.model.Category
import id.wendei.store.data.model.Product
import id.wendei.store.data.remote.util.Resource
import id.wendei.store.data.repository.StoreRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : BaseViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    private val _category = MutableLiveData<Resource<MutableList<Category>>>()
    val category: LiveData<Resource<MutableList<Category>>> = _category

    private val _indicatorCart = MutableLiveData(false)
    val indicatorCart: LiveData<Boolean> = _indicatorCart

    init {
        viewModelScope.launch {
            val initTask = async {
                getProductCategories()
                getProducts()
                getCart()
            }
            initTask.await()
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            storeRepository.getProducts().collect {
                _products.value = it
            }
        }
    }

    private fun getProductCategories() {
        viewModelScope.launch {
            storeRepository.getProductCategories().collect {
                _category.value = it.apply {
                    it.data?.add(0, Category(category = "all", selected = true))
                }
            }
        }
    }

    fun getProductByCategories(category: String) {
        viewModelScope.launch {
            storeRepository.getProductByCategory(category = category).collect {
                _products.value = it
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            storeRepository.addToCart(product = product)
        }
    }

    private fun getCart() {
        viewModelScope.launch {
            storeRepository.getCart().collect {
                _indicatorCart.value = it.isNotEmpty()
            }
        }
    }

}