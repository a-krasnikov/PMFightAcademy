package krasnikov.project.pmfightacademy.activity.booking.services.ui.mapper

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.booking.services.data.Service
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import javax.inject.Inject

class ServiceUIMapper @Inject constructor(
    private val resources: Resources,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun map(source: Service): ServiceUIModel = withContext(defaultDispatcher) {
        ServiceUIModel(
            id = source.id,
            name = source.name,
            price = resources.getString(R.string.text_price, source.price),
            duration = resources.getString(R.string.text_duration)
        )
    }

    suspend fun map(source: List<Service>): List<ServiceUIModel> {
        return source.map { map(it) }
    }
}
