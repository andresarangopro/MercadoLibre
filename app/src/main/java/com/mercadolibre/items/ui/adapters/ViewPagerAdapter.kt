package com.mercadolibre.items.ui.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.mercadolibre.items.databinding.ItemGridProductBinding
import com.mercadolibre.items.databinding.ItemImagesBinding
import com.mercadolibre.items.domain.Picture
import com.mercadolibre.items.domain.ProductListObject
import com.mercadolibre.items.framework.imagemanager.bindImageUrl
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates


class ViewPagerAdapter
@Inject constructor() : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    internal var collection: List<Picture> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picture: Picture) {
            binding?.imageViewMain?.bindImageUrl(
                url = picture.url,
                placeholder = com.mercadolibre.items.R.drawable.ic_camera_alt_black,
                errorPlaceholder = com.mercadolibre.items.R.drawable.ic_broken_image_black
            )
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            ItemImagesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = collection.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)  =
        viewHolder.bind(collection[position])
}