package krasnikov.project.pmfightacademy.activity.booking.services.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.activity.booking.data.BookingService
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.pagination.Pagination
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val bookingService: BookingService,
    pagination: Pagination,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    private val coachesPagination = pagination.pagePagination { page ->
        bookingService.getServices(PER_PAGE, page)
    }

    val flowData = coachesPagination.flowData

    suspend fun loadNextData() {
        withContext(ioDispatcher) {
            coachesPagination.loadNextPage()
        }
    }

    private companion object {
        const val PER_PAGE = 30
    }
}
