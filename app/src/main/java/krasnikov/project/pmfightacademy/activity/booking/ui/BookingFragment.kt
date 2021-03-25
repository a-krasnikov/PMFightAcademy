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
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentBookingBinding
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.getFormattedDate
import krasnikov.project.pmfightacademy.utils.launchWhenStarted
import krasnikov.project.pmfightacademy.utils.setSafeOnClickListener

@AndroidEntryPoint
class BookingFragment : BaseFragment<BookingViewModel, FragmentBookingBinding>() {
    override val viewModel by hiltNavGraphViewModels<BookingViewModel>(R.id.booking_flow)

    private var currentUIState: BookingUIState? = null
        set(value) {
            if (value != null) {
                updateUI(value)
                field = value
            }
        }

    override fun createViewBinding() {
        mBinding = FragmentBookingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()
        setupToolbar()
        setupListeners()
        observeBookingContent()
    }

    private fun setupToolbar() {
        binding.bookingToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupCalendar() {
        binding.calendar.setOnDateChangedListener { _, date, selected ->
            if (selected) viewModel.onChoseDate(date.toString())
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
                val date = calendar.selectedDate.toString()
                val time = (binding.timeSlots.getChildAt(binding.timeSlots.checkedChipId) as Chip)
                    .text.toString()

                viewModel.toBook(date, time)
            }
        }
    }

    private fun observeBookingContent() {
        viewModel.contentBooking.onEach {
            currentUIState = it
        }.launchWhenStarted(lifecycleScope)
    }

    private fun updateUI(bookingUIState: BookingUIState) {
        with(binding) {

            // update ServiceUIState
            with(bookingUIState.serviceState) {
                if (currentUIState?.serviceState != this) {
                    when (this) {
                        is State.Loading -> {
                            tvService.text = getString(R.string.title_choose_service)
                        }
                        is State.Content -> {
                            tvService.text = this.data.name
                        }
                        is State.Error, State.Empty -> {
                        }
                    }
                }
            }

            // update CoachUIState
            with(bookingUIState.coachState) {
                if (currentUIState?.coachState != this) {
                    when (this) {
                        is State.Empty -> {
                            ivAvatarCoach.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_coach
                                )
                            )
                            tvCoach.text = getString(R.string.title_choose_coach)
                            cardCoach.isEnabled = false
                        }
                        is State.Loading -> {
                            ivAvatarCoach.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_coach
                                )
                            )
                            tvCoach.text = getString(R.string.title_choose_coach)
                            cardCoach.isEnabled = true
                        }
                        is State.Content -> {
                            ivAvatarCoach.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.coach_avatar
                                )
                            )
                            tvCoach.text = this.data.fullName
                        }
                        is State.Error -> {
                        }
                    }
                }
            }

            // update CalendarUIState
            with(bookingUIState.calendarState) {
                if (currentUIState?.calendarState != this) {
                    when (this) {
                        is State.Empty -> {
                            calendar.clearSelection()
                            calendar.removeDecorators()
                        }
                        State.Loading -> {
                            calendar.clearSelection()
                            calendar.removeDecorators()
                        }
                        is State.Content -> {
                            binding.calendar.addDecorator(object : DayViewDecorator {
                                override fun shouldDecorate(day: CalendarDay): Boolean {
                                    return !data.contains(day.getFormattedDate())
                                }

                                override fun decorate(view: DayViewFacade) {
                                    view.setDaysDisabled(true)
                                }
                            })
                        }
                        is State.Error -> {
                        }
                    }
                }
            }

            // update TimeSlotsUIState
            with(bookingUIState.timeSlotsState) {
                if (currentUIState?.timeSlotsState != this) {
                    when (this) {
                        is State.Empty -> {
                            tvLabelTime.isVisible = false
                            timeSlots.removeAllViews()
                        }
                        State.Loading -> {
                            tvLabelTime.isVisible = true
                            timeSlots.removeAllViews()
                        }
                        is State.Content -> {
                            this.data.forEach {
                                val chip = Chip(
                                    requireView().context,
                                    null,
                                    R.style.Theme_PMFightAcademy
                                ).apply { text = it }
                                binding.timeSlots.addView(chip)
                            }
                        }
                        is State.Error -> {
                        }
                    }
                }
            }
        }
    }

}
