package krasnikov.project.pmfightacademy.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<V : BaseViewModel, T: ViewBinding> : Fragment() {

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

    abstract fun createViewBinding()

    @Suppress("UnusedPrivateMember")
    private fun collectNavigationEvent() {
        lifecycleScope.launch {
            viewModel.navigationEvent.collect {
                it.navigate(findNavController())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}
