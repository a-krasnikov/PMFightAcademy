package krasnikov.project.pmfightacademy.activity.booking.services.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.activity.booking.ui.BookingViewModel
import krasnikov.project.pmfightacademy.app.ui.base.BaseBottomSheetDialogFragment
import krasnikov.project.pmfightacademy.databinding.FragmentServicesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class ServicesDialogFragment :
    BaseBottomSheetDialogFragment<ServicesViewModel, FragmentServicesBinding>() {

    override val viewModel by viewModels<ServicesViewModel>()

    private val bookingViewModel by viewModels<BookingViewModel>({ requireParentFragment() })

    private val adapter = ServicesAdapter {
        viewModel.loadNextData()
    }

    override fun createViewBinding() {
        mBinding = FragmentServicesBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeServicesContent()
    }

    private fun setupRecycler() {
        binding.rvServices.adapter = adapter.apply {
            onItemClickListener = {
                bookingViewModel.onChoseService(it)
                dismiss()
            }
        }
    }

    private fun observeServicesContent() {
        viewModel.contentServices.onEach {
            adapter.submitData(it)
        }.launchWhenStarted(lifecycleScope)
    }
}
