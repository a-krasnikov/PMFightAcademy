package krasnikov.project.pmfightacademy.activity.booking.coaches.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.activity.booking.ui.BookingViewModel
import krasnikov.project.pmfightacademy.app.pagination.LoadStateAdapter
import krasnikov.project.pmfightacademy.app.ui.base.BaseBottomSheetDialogFragment
import krasnikov.project.pmfightacademy.databinding.FragmentDialogCoachesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class CoachesDialogFragment :
    BaseBottomSheetDialogFragment<CoachesViewModel, FragmentDialogCoachesBinding>() {

    override val viewModel by viewModels<CoachesViewModel>()

    private val bookingViewModel by hiltNavGraphViewModels<BookingViewModel>(R.id.booking_flow)

    private val args: CoachesDialogFragmentArgs by navArgs()

    private val dataAdapter = CoachesAdapter { viewModel.loadNextData() }
    private val loadStateAdapter = LoadStateAdapter { viewModel.loadNextData() }

    override fun createViewBinding() {
        mBinding = FragmentDialogCoachesBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeCoachesContent()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData(args.serviceId)
    }

    private fun setupRecycler() {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            .apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_recycler)
                    ?.let { setDrawable(it) }
            }
        binding.rvCoaches.addItemDecoration(dividerItemDecoration)
        binding.rvCoaches.adapter = ConcatAdapter(
            dataAdapter.apply {
                onItemClickListener = {
                    bookingViewModel.onChoseCoach(it)
                    dismiss()
                }
            }, loadStateAdapter
        )
    }

    private fun observeCoachesContent() {
        viewModel.contentCoaches.onEach {
            loadStateAdapter.state = it.currentState
            dataAdapter.submitList(it.availableData)
        }.launchWhenStarted(lifecycleScope)
    }
}
