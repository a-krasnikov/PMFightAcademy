package krasnikov.project.pmfightacademy.info.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.ui.base.BaseFragment
import krasnikov.project.pmfightacademy.databinding.FragmentInfoBinding
import krasnikov.project.pmfightacademy.utils.State

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel, FragmentInfoBinding>() {
    private var infoJob: Job? = null

    override val viewModel by viewModels<InfoViewModel>()

    override fun createViewBinding() {
        mBinding = FragmentInfoBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        subscribeToContentFlow()
        viewModel.getInfo()
    }

    private fun subscribeToContentFlow() {
        infoJob = lifecycleScope.launch {
            viewModel.infoScreenContent.collect { state ->
                when (state) {
                    is State.Content -> {
                        setIsLoading(false)
                        binding.tvInfoDescription.text = state.data.description
                    }
                    is State.Loading -> {
                        setIsLoading(true)
                    }
                    is State.Error -> {
                        //show error message
                    }
                }
            }
        }

    }

    private fun setIsLoading(isVisible: Boolean) {
        binding.pbInfoLoading.isVisible = isVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        infoJob?.cancel()
        infoJob = null
    }

}
