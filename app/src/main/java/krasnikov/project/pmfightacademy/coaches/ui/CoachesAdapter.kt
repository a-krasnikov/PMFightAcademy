package krasnikov.project.pmfightacademy.coaches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.databinding.RecyclerItemDetailCoachBinding

class CoachesAdapter(loadNextData: () -> Unit) :
    PaginationAdapter<CoachUIModel, CoachesAdapter.CoachViewHolder>(loadNextData = loadNextData) {

    override fun onCreateViewHolder(parent: ViewGroup): CoachViewHolder {
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
