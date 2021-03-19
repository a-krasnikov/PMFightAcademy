package krasnikov.project.pmfightacademy.info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import krasnikov.project.pmfightacademy.databinding.FragmentInfoBinding
import krasnikov.project.pmfightacademy.utils.State

class InfoFragment: Fragment() {
    private val infoViewModel: InfoViewModel by viewModels()
    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToInfoContent()
        infoViewModel.getInfo()
    }

    private fun subscribeToInfoContent() {
        infoViewModel.infoScreenContent.observe(viewLifecycleOwner, { state ->
            when(state) {
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

        })
    }

    private fun setIsLoading(isVisible: Boolean) {
        binding.pbInfoLoading.isVisible = isVisible
    }
}
