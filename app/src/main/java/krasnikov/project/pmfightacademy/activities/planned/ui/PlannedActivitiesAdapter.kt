package krasnikov.project.pmfightacademy.activities.planned.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.activities.planned.ui.model.PlannedActivityUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityPlannedBinding

class PlannedActivitiesAdapter(loadMore: () -> Unit) :
    PaginationAdapter<PlannedActivityUIModel, PlannedActivitiesAdapter.PlannedActivityViewHolder>(
        loadNextData = loadMore
    ) {

    override fun onCreateViewHolder(parent: ViewGroup): PlannedActivityViewHolder {
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
                tvActivityPrice.text = activity.price
            }
        }
    }
}
