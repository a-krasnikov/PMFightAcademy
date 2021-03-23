package krasnikov.project.pmfightacademy.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.utils.Event

abstract class BaseBottomSheetDialogFragment<V : BaseViewModel, T : ViewBinding> :
    BottomSheetDialogFragment() {

    private var eventJob: Job? = null

    protected abstract val viewModel: V
    protected var mBinding: T? = null
    protected val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewBinding()
        return binding.root
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

    private fun showToast(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    abstract fun createViewBinding()

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
