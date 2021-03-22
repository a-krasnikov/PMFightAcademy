package krasnikov.project.pmfightacademy.coaches.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import krasnikov.project.pmfightacademy.databinding.FragmentCoachesBinding
import krasnikov.project.pmfightacademy.utils.launchWhenStarted

@AndroidEntryPoint
class CoachesFragment : Fragment() {

    private lateinit var binding: FragmentCoachesBinding

    private val viewModel by viewModels<CoachesViewModel>()

    private val adapter = CoachesAdapter {
        viewModel.loadNextData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoachesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeCoachesContent()
    }

    private fun setupRecycler() {
        binding.rvCoaches.adapter = adapter
    }

    private fun observeCoachesContent() {
        viewModel.contentCoaches.onEach {
            adapter.submitData(it)
        }.launchWhenStarted(lifecycleScope)
    }
}
