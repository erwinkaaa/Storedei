package id.wendei.store.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import id.wendei.store.R
import id.wendei.store.domain.model.Product
import id.wendei.store.databinding.ItemProductBinding
import id.wendei.store.util.load

enum class ProductAdapterType {
    MAIN, CART
}

class ProductAdapter(
    private val type: ProductAdapterType,
    private val onAddToCartClick: (Product) -> Unit = {},
    private val onRemoveFromCartClick: (Product) -> Unit = {},
    private val onDownloadImageClick: (Product) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val data = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(products: List<Product>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val itemProductBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {
        fun bindItem(data: Product) = with(itemProductBinding) {
            val context = root.context
            ivProduct.load(data.image)
            tvProductName.text = data.title
            tvProductDescription.text = data.description
            rbProduct.rating = data.rating.rate.toFloat()
            tvRatingCount.text = context.getString(R.string.rating_count, data.rating.count)
            ivMore.setOnClickListener {
                PopupMenu(context, ivMore).apply {
                    menuInflater.inflate(R.menu.menu_product, menu)
                    if (type == ProductAdapterType.MAIN) {
                        menu.findItem(R.id.menuRemoveFromCart).isVisible = false
                    } else {
                        menu.findItem(R.id.menuAddToCart).isVisible = false
                    }
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuAddToCart -> {
                                onAddToCartClick(data)
                            }
                            R.id.menuRemoveFromCart -> {
                                onRemoveFromCartClick(data)
                            }
                            R.id.menuDownloadImage -> {
                                onDownloadImageClick(data)
                            }
                        }
                        true
                    }
                }.show()
            }
        }
    }
}