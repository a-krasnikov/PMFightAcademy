package krasnikov.project.pmfightacademy.activity.booking.coaches.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.activity.booking.data.BookingService
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.PageCounterPagination
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import krasnikov.project.pmfightacademy.coaches.data.Coach
import java.lang.IllegalStateException
import javax.inject.Inject

class CoachRepository @Inject constructor(
    private val bookingService: BookingService,
    private val pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    private var coachesPagination: PageCounterPagination<Coach>? = null

    fun flowData(serviceId: Int): Flow<List<Coach>> {
        coachesPagination = pagination.pagePagination { page ->
            bookingService.getCoaches(PER_PAGE, page, serviceId)
        }

        return coachesPagination?.flowData ?: throw IllegalStateException()
    }

    suspend fun loadNextData() {
        withContext(ioDispatcher) {
            coachesPagination?.loadNextPage()
        }
    }

    private companion object {
        const val PER_PAGE = 30
    }
}
