package krasnikov.project.pmfightacademy.info.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.R
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentInfoBinding
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel, FragmentInfoBinding>(R.layout.fragment_info) {
    override val viewModel by viewModels<InfoViewModel>()

    override val bindingFactory = FragmentInfoBinding::bind
    private lateinit var adapter: InfoImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
        subscribeToContentFlow()
        viewModel.getInfo()
    }

    private fun setViewPager() {
        adapter = InfoImagesAdapter()
        binding.rvImages.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(binding.rvImages)
    }

    private fun subscribeToContentFlow() {
        viewModel.infoScreenContent.onEach { state ->
            when (state) {
                is State.Content -> {
                    binding.stateInfo.resetState()
                    binding.tvInfoDescription.text = state.data.description
                    adapter.submitList(state.data.images)
                }
                is State.Loading, State.Empty -> {
                    binding.stateInfo.showLoading()
                }
                is State.Error -> {
                    binding.stateInfo.showError(state.error.errorType.errorStringRes)
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }
}
