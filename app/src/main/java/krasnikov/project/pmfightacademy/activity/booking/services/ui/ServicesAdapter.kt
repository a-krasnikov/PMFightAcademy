package krasnikov.project.pmfightacademy.activity.booking.services.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemServiceBinding

class ServicesAdapter(loadNextData: () -> Unit) :
    PaginationAdapter<ServiceUIModel, ServicesAdapter.ServiceViewHolder>(loadNextData = loadNextData) {

    override fun onCreateViewHolder(parent: ViewGroup): ServiceViewHolder {
        return ServiceViewHolder(
            RecyclerItemServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, item: ServiceUIModel) {
        holder.bind(item)
    }

    class ServiceViewHolder(private val binding: RecyclerItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: ServiceUIModel) {
            with(binding) {
                tvName.text = service.name
                tvPrice.text = service.price
                tvDuration.text = service.duration
            }
        }
    }
}
