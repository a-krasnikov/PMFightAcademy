package krasnikov.project.pmfightacademy.coaches.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.pagination.Pagination

class CoachRepository(
    private val coachesService: CoachesService,
    pagination: Pagination,
    private val ioDispatcher: CoroutineDispatcher,
) {

    private val coachesPagination = pagination.pagePagination { page ->
        coachesService.getCoaches(PER_PAGE, page)
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

/*

private val coachRepository: CoachRepository = CoachRepository(object : CoachesService {

    private val map: MutableMap<Int, List<Coach>> = mutableMapOf()

    init {
        val coach = Coach(
            0,
            "Alex",
            "Tarasov",
            20,
            "In life I am a versatile person. Persistent, purposeful, sociable. I love to set challenging tasks for myself and fulfill them. Since for me sport is fun up to a sweat, I train and train with true pleasure. I sincerely believe that it is the personal trainer who is responsible for the athletic success of his wards. Give me the opportunity to unleash your potential - and you will not regret it for a second!",
            "+380685673421",
            listOf("Boxing")
        )

        val list = buildList {
            repeat(30) {
                this.add(coach)
            }
        }

        repeat(3) {
            map[it] = list
        }
    }

    override suspend fun getCoaches(pageSize: Int, page: Int): List<Coach> {
        delay(2000)
        return map[page] ?: throw Exception()
    }

}, Pagination(), Dispatchers.IO)*/
