package krasnikov.project.pmfightacademy.coaches.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.pagination.LoadStateAdapter
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentCoachesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class CoachesFragment :
    BaseFragment<CoachesViewModel, FragmentCoachesBinding>(R.layout.fragment_coaches) {

    override val viewModel by viewModels<CoachesViewModel>()
    override val bindingFactory = FragmentCoachesBinding::bind

    private val dataAdapter = CoachesAdapter { viewModel.loadCoaches() }
    private val loadStateAdapter = LoadStateAdapter { viewModel.loadCoaches() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeCoachesContent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCoaches()
    }

    private fun setupRecycler() {
        binding.rvCoaches.adapter = ConcatAdapter(dataAdapter, loadStateAdapter)
    }

    private fun observeCoachesContent() {
        viewModel.contentCoaches.onEach {
            loadStateAdapter.state = it.currentState
            dataAdapter.submitList(it.availableData)
        }.launchWhenStarted(lifecycleScope)
    }
}
