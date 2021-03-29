package krasnikov.project.pmfightacademy.activity.booking.ui

import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import krasnikov.project.pmfightacademy.utils.State
import java.lang.Exception

data class BookingUIState(
    val serviceState: State<ServiceUIModel, ErrorWrapper.General> = State.Loading,
    val coachState: State<CoachUIModel, ErrorWrapper.General> = State.Empty,
    val calendarState: State<List<String>, ErrorWrapper.General> = State.Empty,
    val timeSlotsState: State<List<String>, ErrorWrapper.General> = State.Empty,
    val bookingState: State<Unit, ErrorWrapper.General> = State.Empty
)
