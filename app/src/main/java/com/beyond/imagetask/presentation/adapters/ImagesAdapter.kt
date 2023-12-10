package com.beyond.imagetask.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.beyond.imagetask.databinding.ItemImagesBinding
import com.beyond.imagetask.domian.models.ImagesModel
import com.bumptech.glide.Glide


class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.Holder>() {


    var images: ArrayList<ImagesModel>? = null

    private var onClick : ((ImagesModel) -> Unit)? = null

    fun setOnClick (onClick : (ImagesModel)->Unit){
        this.onClick = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = images?.get(position)
        holder.bind(image)
    }

    inner class Holder(val binding: ItemImagesBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                images?.get(layoutPosition)?.let { it1 -> onClick?.invoke(it1) }
            }
        }
        fun bind(image: ImagesModel?) {
            image?.let {
                binding.textCatName.text = it.name
                Glide.with(binding.root.context).load(it.url).into(binding.imageCat)
            }
        }
    }
}