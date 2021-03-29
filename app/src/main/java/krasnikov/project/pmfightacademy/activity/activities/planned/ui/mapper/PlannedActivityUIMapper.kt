package krasnikov.project.pmfightacademy.activity.activities.planned.ui.mapper

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.activities.data.Activity
import krasnikov.project.pmfightacademy.activity.activities.planned.ui.model.PlannedActivityUIModel
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import javax.inject.Inject

class PlannedActivityUIMapper @Inject constructor(
    private val resources: Resources,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun map(source: Activity): PlannedActivityUIModel = withContext(defaultDispatcher) {
        PlannedActivityUIModel(
            serviceName = source.serviceName,
            date = source.date,
            coachFullName = "${source.coachFirstName} ${source.coachLastName}",
            time = source.time,
            price = resources.getString(R.string.activity_price, source.price)
        )
    }

    suspend fun map(list: List<Activity>): List<PlannedActivityUIModel> {
        return withContext(defaultDispatcher) {
            list.map { map(it) }
        }
    }
}
