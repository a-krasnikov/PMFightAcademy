package krasnikov.project.pmfightacademy.activity.booking.ui

import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.utils.State
import java.lang.Exception

data class BookingUIState(
    val serviceState: State<ServiceUIModel, Exception> = State.Loading,
    val coachState: State<CoachUIModel, Exception> = State.Empty,
    val calendarState: State<List<String>, Exception> = State.Empty,
    val timeSlotsState: State<List<String>, Exception> = State.Empty,
    val bookingState: State<Unit, Exception> = State.Empty
)
