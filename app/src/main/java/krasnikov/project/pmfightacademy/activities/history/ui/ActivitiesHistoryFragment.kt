package krasnikov.project.pmfightacademy.activities.history.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentActivitiesHistoryBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class ActivitiesHistoryFragment: BaseFragment<ActivitiesHistoryViewModel, FragmentActivitiesHistoryBinding>() {
    override val viewModel by viewModels<ActivitiesHistoryViewModel>()

    override fun createViewBinding() {
        mBinding = FragmentActivitiesHistoryBinding.inflate(layoutInflater)
    }

    private val adapter = ActivitiesHistoryAdapter {
        viewModel.getActivitiesHistory()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        subscribeToActivitiesHistory()
    }


    private fun setupRecycler() {
        binding.rvActivitiesHistory.adapter = adapter
    }

    private fun subscribeToActivitiesHistory() {
        viewModel.activitiesHistoryContent.onEach {
            adapter.submitData(it)
        }.launchWhenStarted(lifecycleScope)
    }

}