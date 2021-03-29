package krasnikov.project.pmfightacademy.activity.activities.planned.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.activity.activities.data.ActivitiesService
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import javax.inject.Inject

class PlannedActivitiesRepository @Inject constructor(
    private val activitiesService: ActivitiesService,
    pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    private val plannedActivitiesPagination = pagination.pagePagination { page ->
        activitiesService.getPlannedActivities(pageSize, page)
    }

    val plannedActivitiesFlow = plannedActivitiesPagination.flowData

    suspend fun loadPlannedActivities(forceRefresh: Boolean = false) {
        withContext(ioDispatcher) {
            plannedActivitiesPagination.loadNextPage(forceRefresh)
        }
    }


    private companion object {
        const val pageSize = 30
    }
}
