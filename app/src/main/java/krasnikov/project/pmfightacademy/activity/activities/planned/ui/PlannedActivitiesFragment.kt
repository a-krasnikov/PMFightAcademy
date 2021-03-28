package krasnikov.project.pmfightacademy.activity.activities.planned.ui

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
import krasnikov.project.pmfightacademy.databinding.FragmentActivitiesPlannedBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class PlannedActivitiesFragment :
    BaseFragment<PlannedActivitiesViewModel, FragmentActivitiesPlannedBinding>(R.layout.fragment_activities_planned) {

    override val viewModel by viewModels<PlannedActivitiesViewModel>()
    override val bindingFactory = FragmentActivitiesPlannedBinding::bind

    private val dataAdapter = PlannedActivitiesAdapter { viewModel.loadPlannedActivities() }
    private val loadStateAdapter = LoadStateAdapter { viewModel.loadPlannedActivities() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        setupRecyclerView()
        subscribeToPlannedActivitiesFlow()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlannedActivities(forceRefresh = true)
    }

    private fun setupMenu() {
        binding.toolbar.inflateMenu(R.menu.app_bar_planned_activities)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.book -> {
                    viewModel.openBooking()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvActivities.adapter = ConcatAdapter(dataAdapter, loadStateAdapter)
    }

    private fun subscribeToPlannedActivitiesFlow() {
        viewModel.plannedActivitiesContent.onEach {
            loadStateAdapter.state = it.currentState
            dataAdapter.submitList(it.availableData)
        }.launchWhenStarted(lifecycleScope)
    }
}
