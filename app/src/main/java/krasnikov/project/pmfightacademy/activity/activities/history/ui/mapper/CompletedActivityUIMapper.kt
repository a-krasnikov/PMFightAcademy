package krasnikov.project.pmfightacademy.activity.activities.history.ui.mapper

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.activities.data.Activity
import krasnikov.project.pmfightacademy.activity.activities.history.ui.model.CompletedActivityUIModel
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import javax.inject.Inject

class CompletedActivityUIMapper @Inject constructor(
    private val resources: Resources,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun map(source: Activity): CompletedActivityUIModel = withContext(defaultDispatcher) {
        CompletedActivityUIModel(
            serviceName = source.serviceName,
            date = source.date,
            coachFullName = "${source.coachFirstName} ${source.coachLastName}",
            price = resources.getString(R.string.activity_price, source.price)
        )
    }

    suspend fun map(source: List<Activity>): List<CompletedActivityUIModel> {
        return withContext(defaultDispatcher) {
            source.map { map(it) }
        }
    }
}
