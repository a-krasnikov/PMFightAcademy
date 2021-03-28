package krasnikov.project.pmfightacademy.activity.activities.planned.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.activity.activities.planned.ui.model.PlannedActivityUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationListAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityPlannedBinding

class PlannedActivitiesAdapter(loadMore: () -> Unit) :
    PaginationListAdapter<PlannedActivityUIModel, PlannedActivitiesAdapter.PlannedActivityViewHolder>(
        loadNextData = loadMore,
        diffCallback = PlannedActivityDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlannedActivityViewHolder {
        val binding = RecyclerItemActivityPlannedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlannedActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlannedActivityViewHolder, item: PlannedActivityUIModel) {
        holder.bind(item)
    }

    class PlannedActivityViewHolder(private val binding: RecyclerItemActivityPlannedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: PlannedActivityUIModel) {
            with(binding) {
                tvCoachName.text = activity.coachFullName
                tvServiceName.text = activity.serviceName
                tvDate.text = activity.date
                tvTime.text = activity.time
                tvPrice.text = activity.price
            }
        }
    }
}

class PlannedActivityDiffCallback : DiffUtil.ItemCallback<PlannedActivityUIModel>() {
    override fun areItemsTheSame(
        oldItem: PlannedActivityUIModel,
        newItem: PlannedActivityUIModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: PlannedActivityUIModel,
        newItem: PlannedActivityUIModel
    ): Boolean {
        return oldItem == newItem
    }
}
