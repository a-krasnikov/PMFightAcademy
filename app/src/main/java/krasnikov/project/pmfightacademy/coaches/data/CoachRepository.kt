package krasnikov.project.pmfightacademy.coaches.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import javax.inject.Inject

class CoachRepository @Inject constructor(
    private val coachesService: CoachesService,
    pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    private val coachesPagination = pagination.pagePagination { page ->
        coachesService.getCoaches(PER_PAGE, page)
    }

    val flowData = coachesPagination.flowData

    suspend fun loadNextData(forceRefresh: Boolean = false) {
        withContext(ioDispatcher) {
            coachesPagination.loadNextPage(forceRefresh)
        }
    }

    private companion object {
        const val PER_PAGE = 30
    }
}
