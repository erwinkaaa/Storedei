package id.wendei.store.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.wendei.store.base.BaseActivity
import id.wendei.store.data.remote.util.Resource
import id.wendei.store.databinding.ActivityMainBinding
import id.wendei.store.ui.cart.CartActivity
import id.wendei.store.util.invisible
import id.wendei.store.util.visible

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(onClick = {
            if (it.category != "all") {
                viewModel.getProductByCategories(category = it.category)
            } else {
                viewModel.getProducts()
            }
        })
    }

    private val productAdapter by lazy {
        ProductAdapter(
            type = ProductAdapterType.MAIN,
            onAddToCartClick = {
                viewModel.addToCart(product = it)
                Toast.makeText(this@MainActivity, "Added to cart", Toast.LENGTH_SHORT).show()
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
        rvCategory.adapter = categoryAdapter
        rvProduct.adapter = productAdapter

        btnCart.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
            )
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun observations() {
        viewModel.category.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { categories -> categoryAdapter.setData(categories) }
                    viewBinding.shimmerCategory.stopShimmer()
                    viewBinding.shimmerCategory.invisible()
                    viewBinding.rvCategory.visible()
                }
                is Resource.Error -> {
                    viewBinding.shimmerCategory.stopShimmer()
                    viewBinding.shimmerCategory.invisible()
                    viewBinding.rvCategory.visible()
                }
                is Resource.Loading -> {
                    viewBinding.shimmerCategory.startShimmer()
                    viewBinding.shimmerCategory.visible()
                    viewBinding.rvCategory.invisible()
                }
            }
        }

        viewModel.products.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { products -> productAdapter.setData(products) }
                    viewBinding.shimmerProduct.stopShimmer()
                    viewBinding.shimmerProduct.invisible()
                    viewBinding.rvProduct.visible()
                }
                is Resource.Error -> {
                    viewBinding.shimmerProduct.stopShimmer()
                    viewBinding.shimmerProduct.invisible()
                    viewBinding.rvProduct.invisible()
                }
                is Resource.Loading -> {
                    viewBinding.shimmerProduct.startShimmer()
                    viewBinding.shimmerProduct.visible()
                    viewBinding.rvProduct.invisible()
                }
            }
        }

        viewModel.indicatorCart.observe(this) {
            if (it) {
                viewBinding.indicatorCart.visible()
            } else {
                viewBinding.indicatorCart.invisible()
            }
        }
    }
}