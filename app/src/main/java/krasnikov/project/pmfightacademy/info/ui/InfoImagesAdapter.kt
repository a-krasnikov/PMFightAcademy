package krasnikov.project.pmfightacademy.info.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import krasnikov.project.pmfightacademy.databinding.RecyclerItemInfoImageBinding
import krasnikov.project.pmfightacademy.utils.setImageFromGlide

class InfoImagesAdapter :
    RecyclerView.Adapter<InfoImagesAdapter.InfoImageViewHolder>() {

    private var images: List<StorageReference> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoImageViewHolder {
        val binding =
            RecyclerItemInfoImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    fun submitList(list: List<StorageReference>) {
        images = list
        notifyDataSetChanged()
    }

    class InfoImageViewHolder(private val binding: RecyclerItemInfoImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageReference: StorageReference) {
            binding.ivInfoImage.setImageFromGlide(imageReference)
        }
    }
}
