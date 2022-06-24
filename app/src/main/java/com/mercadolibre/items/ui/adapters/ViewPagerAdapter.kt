package com.mercadolibre.items.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.items.databinding.ItemImagesBinding
import com.mercadolibre.items.domain.Picture
import com.mercadolibre.items.framework.imagemanager.bindImageUrl
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
            binding.imageViewMain.bindImageUrl(
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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])
}