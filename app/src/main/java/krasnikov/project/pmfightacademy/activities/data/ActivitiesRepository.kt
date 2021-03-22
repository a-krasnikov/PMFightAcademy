package krasnikov.project.pmfightacademy.activities.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val activitiesService: ActivitiesService,
    pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    private val plannedActivitiesPagination = pagination.pagePagination { page ->
        activitiesService.getPlannedActivities(pageSize, page)
    }

    val plannedActivitiesFlow = plannedActivitiesPagination.flowData

    suspend fun getPlannedActivities() {
        withContext(ioDispatcher) {
            plannedActivitiesPagination.loadNextPage()
        }
    }


    private companion object {
        const val pageSize = 30
    }
}
