package id.wendei.store.ui.cart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.wendei.store.base.BaseActivity
import id.wendei.store.databinding.ActivityCartBinding
import id.wendei.store.ui.main.ProductAdapter
import id.wendei.store.ui.main.ProductAdapterType

@AndroidEntryPoint
class CartActivity
    : BaseActivity<ActivityCartBinding, CartViewModel>(ActivityCartBinding::inflate) {

    override val viewModel: CartViewModel by viewModels()

    private val productAdapter by lazy {
        ProductAdapter(
            type = ProductAdapterType.CART,
            onRemoveFromCartClick = {
                viewModel.removeFromCart(product = it)
                Toast.makeText(this@CartActivity, "Removed from cart", Toast.LENGTH_SHORT).show()
            },
            onDownloadImageClick = {
                viewModel.downloadImage(it.image, application)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observations()
    }

    private fun initViews() = with(viewBinding) {
        rvProduct.adapter = productAdapter
        ivBack.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun observations() {
        viewModel.product.observe(this) {
            productAdapter.setData(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}