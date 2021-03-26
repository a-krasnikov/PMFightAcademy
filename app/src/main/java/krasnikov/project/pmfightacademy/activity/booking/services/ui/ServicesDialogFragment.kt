package krasnikov.project.pmfightacademy.activity.booking.services.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.booking.ui.BookingViewModel
import krasnikov.project.pmfightacademy.app.ui.base.BaseBottomSheetDialogFragment
import krasnikov.project.pmfightacademy.databinding.FragmentDialogServicesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class ServicesDialogFragment :
    BaseBottomSheetDialogFragment<ServicesViewModel, FragmentDialogServicesBinding>() {

    override val viewModel by viewModels<ServicesViewModel>()

    private val bookingViewModel by hiltNavGraphViewModels<BookingViewModel>(R.id.booking_flow)

    private val adapter = ServicesAdapter {
        viewModel.loadNextData()
    }

    override fun createViewBinding() {
        mBinding = FragmentDialogServicesBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeServicesContent()
    }

    private fun setupRecycler() {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            .apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_recycler)
                    ?.let { setDrawable(it) }
            }
        binding.rvServices.addItemDecoration(dividerItemDecoration)
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
