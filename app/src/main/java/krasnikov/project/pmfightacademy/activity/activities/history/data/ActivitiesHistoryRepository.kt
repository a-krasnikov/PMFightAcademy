package krasnikov.project.pmfightacademy.activity.activities.history.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.activity.activities.data.ActivitiesService
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import javax.inject.Inject

class ActivitiesHistoryRepository @Inject constructor(
    private val activitiesService: ActivitiesService,
    pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    private val activitiesHistoryPagination = pagination.pagePagination { page ->
        activitiesService.getActivitiesHistory(pageSize, page)
    }

    val activitiesHistoryFlow = activitiesHistoryPagination.flowData

    suspend fun loadActivitiesHistory(forceRefresh: Boolean = false) {
        withContext(ioDispatcher) {
            activitiesHistoryPagination.loadNextPage(forceRefresh)
        }
    }

    private companion object {
        const val pageSize = 30
    }
}
