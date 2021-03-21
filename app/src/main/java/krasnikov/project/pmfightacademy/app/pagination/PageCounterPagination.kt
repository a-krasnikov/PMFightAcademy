package krasnikov.project.pmfightacademy.app.pagination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.atomic.AtomicInteger

class PageCounterPagination<T>(private val getRequest: suspend (Int) -> List<T>) {

    private val page = AtomicInteger(0)

    private val dataSharedFlow = MutableSharedFlow<List<T>>(replay = 1)

    val flowData: Flow<List<T>> = dataSharedFlow

    suspend fun loadNextPage() {
        val oldData = dataSharedFlow.replayCache.firstOrNull() ?: emptyList()
        val newData = getRequest(page.get())

        dataSharedFlow.emit(oldData + newData)

        page.incrementAndGet()
    }
}

class Pagination {

    fun <T> pagePagination(getRequest: suspend (Int) -> List<T>): PageCounterPagination<T> {
        return PageCounterPagination(getRequest)
    }
}
