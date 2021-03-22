package krasnikov.project.pmfightacademy.app.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class PageCounterPagination<T>(private val getRequest: suspend (Int) -> PagedResponse<T>) {

    private val page = AtomicInteger(0)

    private val loadMore = AtomicBoolean(true)

    private val dataSharedFlow = MutableSharedFlow<List<T>>(replay = 1)

    val flowData: Flow<List<T>> = dataSharedFlow

    suspend fun loadNextPage() {
        if (loadMore.equals(true)) {
            val oldData = dataSharedFlow.replayCache.firstOrNull() ?: emptyList()
            val newData = getRequest(page.get())

            dataSharedFlow.emit(oldData + newData.data)

            loadMore.set(newData.pagination.hasNextPage)

            page.set(newData.pagination.page + 1)
        }

    }
}

class Pagination @Inject constructor() {

    fun <T> pagePagination(getRequest: suspend (Int) -> PagedResponse<T>): PageCounterPagination<T> {
        return PageCounterPagination(getRequest)
    }
}
