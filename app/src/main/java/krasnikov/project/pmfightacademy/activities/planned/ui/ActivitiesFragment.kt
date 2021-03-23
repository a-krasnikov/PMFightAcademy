package krasnikov.project.pmfightacademy.activities.planned.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
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
        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.planned_activities, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.book -> {
                //navigate to booking
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
