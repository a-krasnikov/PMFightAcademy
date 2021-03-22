package krasnikov.project.pmfightacademy.coaches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.pagination.PaginationAdapter
import krasnikov.project.pmfightacademy.coaches.data.Coach
import krasnikov.project.pmfightacademy.databinding.RecyclerItemDetailCoachBinding

class CoachesAdapter(loadNextData: () -> Unit) :
    PaginationAdapter<Coach, CoachesAdapter.CoachViewHolder>(loadNextData = loadNextData) {

    override fun onCreateViewHolder(parent: ViewGroup): CoachViewHolder {
        return CoachViewHolder(
            RecyclerItemDetailCoachBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoachViewHolder, item: Coach) {
        holder.bind(item)
    }

    class CoachViewHolder(private val binding: RecyclerItemDetailCoachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val resources = itemView.resources

        fun bind(coach: Coach) {
            with(binding) {
                ivAvatar.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.coach_avatar,
                        null
                    )
                )
                tvName.text =
                    resources.getString(R.string.text_coach_name, coach.firstName, coach.lastName)
                tvAge.text = resources.getString(R.string.text_coach_age, coach.age)
                tvService.text = coach.services[0]
                tvPhoneNumber.text = coach.phoneNumber
                tvDescription.text = coach.description
            }
        }
    }
}
