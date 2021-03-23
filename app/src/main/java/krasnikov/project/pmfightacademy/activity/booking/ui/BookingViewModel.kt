package krasnikov.project.pmfightacademy.activity.booking.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.utils.Event

class BookingViewModel : BaseViewModel() {

    override fun handleError(throwable: Throwable) {
    }

    fun onServiceClick() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(BookingFragmentDirections.actionBookingToServices()))
        }
    }

    fun onCoachClick() {

    }

    fun onChoseService(service: ServiceUIModel) {

    }
}
