package krasnikov.project.pmfightacademy.coaches.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentCoachesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class CoachesFragment : BaseFragment<CoachesViewModel, FragmentCoachesBinding>() {

    override val viewModel by viewModels<CoachesViewModel>()

    private val adapter = CoachesAdapter {
        viewModel.loadNextData()
    }

    override fun createViewBinding() {
        mBinding = FragmentCoachesBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeCoachesContent()
    }

    private fun setupRecycler() {
        binding.rvCoaches.adapter = adapter
    }

    private fun observeCoachesContent() {
        viewModel.contentCoaches.onEach {
            adapter.submitData(it)
        }.launchWhenStarted(lifecycleScope)
    }
}
