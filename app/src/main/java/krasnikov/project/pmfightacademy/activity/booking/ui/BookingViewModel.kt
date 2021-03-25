package krasnikov.project.pmfightacademy.activity.booking.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.activity.booking.data.Booking
import krasnikov.project.pmfightacademy.activity.booking.data.BookingService
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingService: BookingService,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _contentBooking = MutableStateFlow(BookingUIState())
    val contentBooking = _contentBooking.asStateFlow()

    override fun handleError(throwable: Throwable) {}

    fun onServiceClick() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(BookingFragmentDirections.actionBookingToServicesDialog()))
        }
    }

    fun onCoachClick() {
        viewModelScope.launch {
            with(_contentBooking.value) {
                if (serviceState is State.Content) {
                    eventChannel.send(
                        Event.Navigation(
                            BookingFragmentDirections.actionBookingToCoachesDialog(
                                serviceState.data.id
                            )
                        )
                    )
                }
            }
        }
    }

    fun onChoseService(service: ServiceUIModel) {
        _contentBooking.value = _contentBooking.value.copy(
            serviceState = State.Content(service),
            coachState = State.Loading,
            calendarState = State.Empty,
            timeSlotsState = State.Empty,
            bookingState = State.Empty
        )
    }

    fun onChoseCoach(coach: CoachUIModel) {
        val serviceState = _contentBooking.value.serviceState
        if (serviceState is State.Content) {
            _contentBooking.value = _contentBooking.value.copy(
                coachState = State.Content(coach),
                calendarState = State.Loading,
                timeSlotsState = State.Empty,
                bookingState = State.Empty
            )

            viewModelScope.launch(ioDispatcher) {
                val dates = bookingService.getAvailableDates(
                    serviceState.data.id,
                    coach.id
                )
                _contentBooking.value =
                    _contentBooking.value.copy(calendarState = State.Content(dates))
            }
        }
    }

    fun onChoseDate(date: String) {
        with(_contentBooking.value) {
            if (serviceState is State.Content && coachState is State.Content) {
                _contentBooking.value = _contentBooking.value.copy(timeSlotsState = State.Loading)

                viewModelScope.launch(ioDispatcher) {
                    val timeSlots = bookingService.getAvailableTimeSlots(
                        serviceState.data.id,
                        coachState.data.id,
                        date
                    )
                    _contentBooking.value = this@with.copy(
                        timeSlotsState = State.Content(timeSlots),
                        bookingState = State.Empty
                    )
                }
            }
        }
    }

    fun toBook(date: String, time: String) {
        with(_contentBooking.value) {
            if (serviceState is State.Content && coachState is State.Content
                && calendarState is State.Content && timeSlotsState is State.Content
            ) {
                _contentBooking.value = this.copy(bookingState = State.Loading)
                viewModelScope.launch(ioDispatcher) {
                    bookingService.book(
                        Booking(
                            date,
                            time,
                            serviceState.data.id,
                            coachState.data.id
                        )
                    )

                    _contentBooking.value = this@with.copy(bookingState = State.Content(Unit))
                }
            }
        }
    }
}

