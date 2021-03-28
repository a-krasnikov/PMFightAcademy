package krasnikov.project.pmfightacademy.coaches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.pagination.PaginationListAdapter
import krasnikov.project.pmfightacademy.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.databinding.RecyclerItemDetailCoachBinding

class CoachesAdapter(loadNextData: () -> Unit) :
    PaginationListAdapter<CoachUIModel, CoachesAdapter.CoachViewHolder>(
        loadNextData = loadNextData,
        diffCallback = CoachesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        return CoachViewHolder(
            RecyclerItemDetailCoachBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoachViewHolder, item: CoachUIModel) {
        holder.bind(item)
    }

    class CoachViewHolder(private val binding: RecyclerItemDetailCoachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coach: CoachUIModel) {
            with(binding) {
                ivAvatar.setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, R.drawable.coach_avatar)
                )
                tvName.text = coach.fullName
                tvAge.text = coach.age
                tvServices.text = coach.services
                tvPhoneNumber.text = coach.phoneNumber
                tvDescription.text = coach.description
            }
        }
    }
}

class CoachesDiffCallback : DiffUtil.ItemCallback<CoachUIModel>() {
    override fun areItemsTheSame(oldItem: CoachUIModel, newItem: CoachUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoachUIModel, newItem: CoachUIModel): Boolean {
        return oldItem == newItem
    }
}
