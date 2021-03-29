package krasnikov.project.pmfightacademy.activity.activities.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.activity.activities.history.ui.model.CompletedActivityUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationListAdapter
import krasnikov.project.pmfightacademy.databinding.RecyclerItemActivityCompletedBinding

class ActivitiesHistoryAdapter(loadMore: () -> Unit) :
    PaginationListAdapter<CompletedActivityUIModel, ActivitiesHistoryAdapter.CompletedActivityViewHolder>(
        loadNextData = loadMore,
        diffCallback = CompletedActivityDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedActivityViewHolder {
        val binding = RecyclerItemActivityCompletedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletedActivityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompletedActivityViewHolder,
        item: CompletedActivityUIModel
    ) {
        holder.bind(item)
    }

    class CompletedActivityViewHolder(private val binding: RecyclerItemActivityCompletedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: CompletedActivityUIModel) {
            with(binding) {
                tvCoachName.text = activity.coachFullName
                tvServiceName.text = activity.serviceName
                tvDate.text = activity.date
                tvPrice.text = activity.price
            }
        }
    }
}

class CompletedActivityDiffCallback : DiffUtil.ItemCallback<CompletedActivityUIModel>() {
    override fun areItemsTheSame(
        oldItem: CompletedActivityUIModel,
        newItem: CompletedActivityUIModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CompletedActivityUIModel,
        newItem: CompletedActivityUIModel
    ): Boolean {
        return oldItem == newItem
    }
}
