package krasnikov.project.pmfightacademy.activity.booking.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentBookingBinding
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.getFormattedDate
import krasnikov.project.pmfightacademy.utils.launchWhenStarted
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener
import krasnikov.project.pmfightacademy.utils.ErrorWrapper

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class BookingFragment :
    BaseFragment<BookingViewModel, FragmentBookingBinding>(R.layout.fragment_booking) {

    override val viewModel by hiltNavGraphViewModels<BookingViewModel>(R.id.booking_flow)
    override val bindingFactory = FragmentBookingBinding::bind

    private var currentUIState: BookingUIState? = null
        set(value) {
            if (value != null) {
                updateUI(value)
                field = value
            }
        }

    private val disableAllDaysDecorator = object : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return true
        }

        override fun decorate(view: DayViewFacade) {
            view.setDaysDisabled(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()
        setupToolbar()
        setupListeners()
        observeBookingContent()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupCalendar() {
        binding.calendar.setOnDateChangedListener { _, date, selected ->
            if (selected) viewModel.onChoseDate(date.getFormattedDate())
        }
    }

    private fun setupListeners() {
        with(binding) {
            cardService.setSafeOnClickListener {
                viewModel.onServiceClick()
            }
            cardCoach.setSafeOnClickListener {
                viewModel.onCoachClick()
            }

            btnToBook.setSafeOnClickListener {
                val date = calendar.selectedDate?.getFormattedDate()
                val chipTimeSlots =
                    binding.timeSlots.getChildAt(binding.timeSlots.checkedChipId) as? Chip
                if (date != null && chipTimeSlots != null) {
                    viewModel.toBook(date, chipTimeSlots.text.toString())
                }
            }

            timeSlots.setOnCheckedChangeListener { _, _ ->
                binding.btnToBook.isEnabled = true
            }
        }
    }

    private fun observeBookingContent() {
        viewModel.contentBooking.onEach {
            currentUIState = it
        }.launchWhenStarted(lifecycleScope)
    }

    private fun updateUI(bookingUIState: BookingUIState) {
        if (currentUIState?.serviceState != bookingUIState.serviceState)
            updateServiceState(bookingUIState.serviceState)

        if (currentUIState?.coachState != bookingUIState.coachState)
            updateCoachState(bookingUIState.coachState)

        if (currentUIState?.calendarState != bookingUIState.calendarState)
            updateCalendarState(bookingUIState.calendarState, bookingUIState.coachState)

        if (currentUIState?.timeSlotsState != bookingUIState.timeSlotsState)
            updateTimeSlotsState(bookingUIState.timeSlotsState)

        if (currentUIState?.bookingState != bookingUIState.bookingState)
            updateBookingState(bookingUIState.bookingState)
    }

    private fun updateServiceState(serviceState: State<ServiceUIModel, ErrorWrapper.General>) {
        with(binding) {
            when (serviceState) {
                is State.Loading -> {
                    tvService.text = getString(R.string.title_choose_service)
                }
                is State.Content -> {
                    tvService.text = serviceState.data.name
                }
                is State.Error, State.Empty -> {
                }
            }
        }
    }

    private fun updateCoachState(coachState: State<CoachUIModel, ErrorWrapper.General>) {
        with(binding) {
            when (coachState) {
                is State.Empty -> {
                    ivAvatarCoach.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_coach)
                    )
                    tvCoach.text = getString(R.string.title_choose_coach)
                    cardCoach.isEnabled = false
                }
                is State.Loading -> {
                    ivAvatarCoach.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_coach)
                    )
                    tvCoach.text = getString(R.string.title_choose_coach)
                    cardCoach.isEnabled = true
                }
                is State.Content -> {
                    ivAvatarCoach.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.coach_avatar)
                    )
                    tvCoach.text = coachState.data.fullName
                }
                is State.Error -> {
                }
            }
        }
    }

    private fun updateCalendarState(
        calendarState: State<List<String>, ErrorWrapper.General>,
        coachState: State<CoachUIModel, ErrorWrapper.General>
    ) {
        with(binding) {
            when (calendarState) {
                is State.Empty -> {
                    calendar.clearSelection()
                    calendar.removeDecorators()
                    stateCalendar.resetState()
                    calendar.addDecorator(disableAllDaysDecorator)
                }
                State.Loading -> {
                    calendar.clearSelection()
                    calendar.removeDecorators()
                    stateCalendar.showLoading()
                }
                is State.Content -> {
                    calendar.removeDecorators()
                    stateCalendar.resetState()
                    calendar.addDecorator(object : DayViewDecorator {
                        override fun shouldDecorate(day: CalendarDay): Boolean {
                            return !calendarState.data.contains(day.getFormattedDate())
                        }

                        override fun decorate(view: DayViewFacade) {
                            view.setDaysDisabled(true)
                        }
                    })
                }
                is State.Error -> {
                    stateCalendar.showError(calendarState.error.errorType.errorStringRes) {
                        if (coachState is State.Content) {
                            viewModel.onChoseCoach(coachState.data)
                        }
                    }
                }
            }
        }
    }

    private fun updateTimeSlotsState(timeSlotsState: State<List<String>, ErrorWrapper.General>) {
        with(binding) {
            when (timeSlotsState) {
                is State.Empty -> {
                    tvLabelTime.isVisible = false
                    timeSlots.removeAllViews()
                    stateTimeSlots.resetState()
                }
                State.Loading -> {
                    tvLabelTime.isVisible = true
                    timeSlots.removeAllViews()
                    stateTimeSlots.showLoading()
                }
                is State.Content -> {
                    stateTimeSlots.resetState()
                    createTimeSlots(timeSlotsState.data)
                }
                is State.Error -> {
                    stateTimeSlots.showError(timeSlotsState.error.errorType.errorStringRes) {
                        calendar.selectedDate?.let { viewModel.onChoseDate(it.getFormattedDate()) }
                    }
                }
            }
        }
    }

    private fun updateBookingState(bookingState: State<Unit, ErrorWrapper.General>) {
        with(binding) {
            when (bookingState) {
                is State.Empty -> {
                    btnToBook.isEnabled = false
                    stateBooking.resetState()
                }
                State.Loading -> {
                    stateBooking.showLoading()
                }
                is State.Content -> {
                    stateBooking.resetState()
                    findNavController().popBackStack()
                }
                is State.Error -> {
                    showToast(bookingState.error.errorType.errorStringRes)
                }
            }
        }
    }

    private fun createTimeSlots(slots: List<String>) {
        slots.forEach {
            val chip = Chip(requireContext())
                .apply {
                    chipBackgroundColor = resources.getColorStateList(R.color.chip_color, null)
                    text = it
                    isClickable = true
                    isCheckable = true
                    isCheckedIconVisible = false
                    setTextColor(resources.getColor(R.color.white, null))
                }

            binding.timeSlots.addView(chip)
        }
    }

}
