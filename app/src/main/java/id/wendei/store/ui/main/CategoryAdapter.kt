package id.wendei.store.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.wendei.store.domain.model.Category
import id.wendei.store.databinding.ItemCategoryBinding

class CategoryAdapter(private val onClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var lastSelectedCategory: Category? = null

    private val data = mutableListOf<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(categories: List<Category>) {
        data.clear()
        data.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
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

    inner class ViewHolder(private val itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun bindItem(data: Category) {
            itemCategoryBinding.chipCategory.text = data.category
            itemCategoryBinding.chipCategory.isChecked = data.selected
            itemCategoryBinding.chipCategory.setOnClickListener {
                setSelected(data)
                if (lastSelectedCategory == null) {
                    onClick.invoke(data)
                    lastSelectedCategory = data
                } else {
                    if (lastSelectedCategory != data) {
                        onClick.invoke(data)
                        lastSelectedCategory = data
                    }
                }
            }
        }
    }

    private fun setSelected(category: Category) {
        data.forEachIndexed { index, item ->
            item.selected = item == category
            notifyItemChanged(index)
        }
    }

}