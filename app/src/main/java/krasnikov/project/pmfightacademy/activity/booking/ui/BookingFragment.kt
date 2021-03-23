package krasnikov.project.pmfightacademy.activity.booking.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentBookingBinding

class BookingFragment : BaseFragment<BookingViewModel, FragmentBookingBinding>() {
    override val viewModel by viewModels<BookingViewModel>()

    override fun createViewBinding() {
        mBinding = FragmentBookingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBookingContent()
    }

    private fun observeBookingContent() {

    }
}
