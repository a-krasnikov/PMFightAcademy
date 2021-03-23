package krasnikov.project.pmfightacademy.activities.planned.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityPlannedBinding

class PlannedActivitiesAdapter(loadMore: () -> Unit) :
    PaginationAdapter<Activity, PlannedActivitiesAdapter.PlannedActivityViewHolder>(loadNextData = loadMore) {

    override fun onCreateViewHolder(parent: ViewGroup): PlannedActivityViewHolder {
        val binding = RecyclerItemActivityPlannedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlannedActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlannedActivityViewHolder, item: Activity) {
        holder.bind(item)
    }

    class PlannedActivityViewHolder(private val binding: RecyclerItemActivityPlannedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val resources = itemView.resources

        fun bind(activity: Activity) {
            with(binding) {
                tvCoachName.text = resources.getString(
                    R.string.activity_coach_name,
                    activity.coachFirstName,
                    activity.coachLastName
                )

                tvServiceName.text = activity.serviceName
                tvDate.text = activity.date
                tvTime.text = activity.time
            }
        }
    }
}
