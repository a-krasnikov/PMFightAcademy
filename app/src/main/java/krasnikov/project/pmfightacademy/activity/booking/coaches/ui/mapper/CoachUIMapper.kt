package krasnikov.project.pmfightacademy.activity.booking.coaches.ui.mapper

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import krasnikov.project.pmfightacademy.coaches.data.Coach
import javax.inject.Inject

class CoachUIMapper @Inject constructor(
    private val resources: Resources,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun map(source: Coach): CoachUIModel = withContext(defaultDispatcher) {
        CoachUIModel(
            id = source.id,
            fullName = "${source.firstName} ${source.lastName}",
            age = resources.getString(R.string.text_coach_age, source.age),
            phoneNumber = source.phoneNumber,
        )
    }

    suspend fun map(source: List<Coach>): List<CoachUIModel> {
        return source.map { map(it) }
    }
}
