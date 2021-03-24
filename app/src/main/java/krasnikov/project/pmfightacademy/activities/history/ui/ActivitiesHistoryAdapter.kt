package krasnikov.project.pmfightacademy.activities.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityCompletedBinding

class ActivitiesHistoryAdapter(loadMore: () -> Unit) :
    PaginationAdapter<Activity, ActivitiesHistoryAdapter.CompletedActivityViewHolder>(loadNextData = loadMore) {


    override fun onCreateViewHolder(parent: ViewGroup): CompletedActivityViewHolder {
        val binding = RecyclerItemActivityCompletedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletedActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedActivityViewHolder, item: Activity) {
        holder.bind(item)
    }

    class CompletedActivityViewHolder(private val binding: RecyclerItemActivityCompletedBinding) :
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
                tvActivityPrice.text = resources.getString(R.string.activity_price, activity.price)
            }
        }
    }

}
