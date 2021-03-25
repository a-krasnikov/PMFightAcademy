package krasnikov.project.pmfightacademy.app.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class PageCounterPagination<T>(private val getRequest: suspend (Int) -> ResponseWithPaginationModel<T>) {

    private val page = AtomicInteger(1)
    private var hasNextPage = AtomicBoolean(true)

    private val dataSharedFlow = MutableSharedFlow<List<T>>(replay = 1)

    val flowData: Flow<List<T>> = dataSharedFlow

    suspend fun loadNextPage() {
        val oldData = dataSharedFlow.replayCache.firstOrNull() ?: emptyList()
        if (hasNextPage.get()) {
            val response = getRequest(page.get())

            dataSharedFlow.emit(oldData + response.data)

            hasNextPage.set(response.pagination.hasNextPage)
            page.incrementAndGet()
        } else {
            dataSharedFlow.emit(oldData)
        }
    }
}

class Pagination @Inject constructor() {

    fun <T> pagePagination(getRequest: suspend (Int) -> ResponseWithPaginationModel<T>): PageCounterPagination<T> {
        return PageCounterPagination(getRequest)
    }
}
