package com.mercadolibre.items.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.items.R
import com.mercadolibre.items.databinding.ItemGridProductBinding
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.framework.imagemanager.bindImageUrl
import javax.inject.Inject
import kotlin.properties.Delegates

class ProductListAdapter
@Inject constructor() : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    internal var collection: List<ProductListObject> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (ProductListObject) -> Unit = { _ -> }

    class ViewHolder(private val binding: ItemGridProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productListObject: ProductListObject, clickListener: (ProductListObject) -> Unit) {
            binding.product = productListObject

            binding.productImage.bindImageUrl(
                url = productListObject.thumbnail,
                placeholder = R.drawable.ic_camera_alt_black,
                errorPlaceholder = R.drawable.ic_broken_image_black
            )
            binding.root.setOnClickListener {
                clickListener(
                    productListObject
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemGridProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount(): Int = collection.size
}