package id.wendei.store.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wendei.store.base.BaseViewModel
import id.wendei.store.data.model.Product
import id.wendei.store.data.repository.StoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : BaseViewModel() {

    private val _product = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _product

    init {
        getCart()
    }

    private fun getCart() {
        viewModelScope.launch {
            storeRepository.getCart().collect {
                _product.value = it
            }
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            storeRepository.removeFromCart(product = product)
        }
    }

}