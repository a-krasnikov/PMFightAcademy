package krasnikov.project.pmfightacademy.app.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class PageCounterPagination<T>(
    private val startPage: Int,
    private val getRequest: suspend (Int) -> ResponseWithPaginationModel<T>
) {

    private val page = AtomicInteger(startPage)
    private var hasNextPage = AtomicBoolean(true)

    private val dataSharedFlow = MutableSharedFlow<List<T>>(replay = 1)

    val flowData: Flow<List<T>> = dataSharedFlow

    suspend fun loadNextPage(forceRefresh: Boolean = false) {
        if (forceRefresh) {
            page.set(startPage)
            with(getRequest(page.get())) {
                dataSharedFlow.emit(data)

                hasNextPage.set(pagination.hasNextPage)
                page.incrementAndGet()
            }
        } else {
            val oldData = dataSharedFlow.replayCache.firstOrNull() ?: emptyList()
            if (hasNextPage.get()) {
                with(getRequest(page.get())) {
                    dataSharedFlow.emit(oldData + data)

                    hasNextPage.set(pagination.hasNextPage)
                    page.incrementAndGet()
                }
            } else {
                dataSharedFlow.emit(oldData)
            }
        }
    }
}

class Pagination @Inject constructor() {

    fun <T> pagePagination(
        startPage: Int = 1,
        getRequest: suspend (Int) -> ResponseWithPaginationModel<T>
    ): PageCounterPagination<T> {
        return PageCounterPagination(startPage, getRequest)
    }
}
