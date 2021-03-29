package krasnikov.project.pmfightacademy.activity.booking.services.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationListAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemBookingServiceBinding

class ServicesAdapter(loadNextData: () -> Unit) :
    PaginationListAdapter<ServiceUIModel, ServicesAdapter.ServiceViewHolder>(
        loadNextData = loadNextData,
        diffCallback = ServicesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(
            RecyclerItemBookingServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, item: ServiceUIModel) {
        holder.bind(item)
    }

    class ServiceViewHolder(private val binding: RecyclerItemBookingServiceBinding) :
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

class ServicesDiffCallback : DiffUtil.ItemCallback<ServiceUIModel>() {
    override fun areItemsTheSame(oldItem: ServiceUIModel, newItem: ServiceUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ServiceUIModel, newItem: ServiceUIModel): Boolean {
        return oldItem == newItem
    }
}
