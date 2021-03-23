package krasnikov.project.pmfightacademy.activities.planned.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentPlannedActivitiesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class ActivitiesFragment: BaseFragment<PlannedActivitiesViewModel, FragmentPlannedActivitiesBinding>() {

    override val viewModel by viewModels<PlannedActivitiesViewModel>()

    override fun createViewBinding() {
        mBinding = FragmentPlannedActivitiesBinding.inflate(layoutInflater)
    }

    private val adapter = PlannedActivitiesAdapter {
        viewModel.getPlannedActivities()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToPlannedActivitiesFlow()
    }

    private fun setupRecyclerView() {
        binding.rvActivities.adapter = adapter
    }

    private fun subscribeToPlannedActivitiesFlow() {
        viewModel.plannedActivitiesContent.onEach {
            adapter.submitData(it)
        }.launchWhenStarted(lifecycleScope)
    }

}
