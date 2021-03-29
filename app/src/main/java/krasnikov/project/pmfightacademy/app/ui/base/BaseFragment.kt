package krasnikov.project.pmfightacademy.app.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.utils.Event

abstract class BaseFragment<V : BaseViewModel, T : ViewBinding>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected abstract val viewModel: V

    protected abstract val bindingFactory: (View) -> T
    private var mBinding: T? = null
    protected val binding get() = mBinding!!

    private var eventJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = bindingFactory(requireView())
    }

    override fun onStart() {
        super.onStart()

        eventJob = viewModel.eventFlow
            .onEach {
                when (it) {
                    is Event.Navigation -> {
                        findNavController().navigate(it.directions)
                    }
                    is Event.Message -> {
                        showToast(it.idRes)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        eventJob?.cancel()
    }

    protected fun showToast(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
