package krasnikov.project.pmfightacademy.app.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    protected abstract val viewModel: V

    @Suppress("UnusedPrivateMember")
    private suspend fun collectNavigationEvent() {
        viewModel.navigationEvent.collect {
            it.navigate(findNavController())
        }
    }

}
