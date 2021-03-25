package krasnikov.project.pmfightacademy.activities.planned.ui.mapper

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.activities.history.ui.model.CompletedActivityUIModel
import krasnikov.project.pmfightacademy.activities.planned.ui.model.PlannedActivityUIModel
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import javax.inject.Inject

class PlannedActivityUIMapper @Inject constructor(
    private val resources: Resources,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun map(list: List<Activity>): List<PlannedActivityUIModel> {
        return withContext(defaultDispatcher) {
            list.map { activity ->
                PlannedActivityUIModel(
                    serviceName = activity.serviceName,
                    date = activity.date,
                    coachFullName = resources.getString(
                        R.string.activity_coach_name,
                        activity.coachFirstName,
                        activity.coachLastName
                    ),
                    time = activity.time,
                    price = resources.getString(R.string.activity_price, activity.price)
                )
            }
        }
    }

}