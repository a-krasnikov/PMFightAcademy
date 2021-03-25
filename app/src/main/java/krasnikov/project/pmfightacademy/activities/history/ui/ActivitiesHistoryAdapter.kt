package krasnikov.project.pmfightacademy.activities.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.activities.history.ui.model.CompletedActivityUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityCompletedBinding

class ActivitiesHistoryAdapter(loadMore: () -> Unit) :
    PaginationAdapter<CompletedActivityUIModel, ActivitiesHistoryAdapter.CompletedActivityViewHolder>(loadNextData = loadMore) {


    override fun onCreateViewHolder(parent: ViewGroup): CompletedActivityViewHolder {
        val binding = RecyclerItemActivityCompletedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletedActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedActivityViewHolder, item: CompletedActivityUIModel) {
        holder.bind(item)
    }

    class CompletedActivityViewHolder(private val binding: RecyclerItemActivityCompletedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: CompletedActivityUIModel) {
            with(binding) {
                tvCoachName.text = activity.coachFullName

                tvServiceName.text = activity.serviceName
                tvDate.text = activity.date
                tvActivityPrice.text = activity.price
            }
        }
    }

}
