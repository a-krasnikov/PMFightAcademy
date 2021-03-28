package krasnikov.project.pmfightacademy.app.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.databinding.RecyclerStateViewBinding

class LoadStateAdapter(private val retry: () -> Unit) :
    RecyclerView.Adapter<LoadStateAdapter.LoadStateViewHolder>() {

    var state: LoadState = LoadState.Empty
        set(value) {
            val oldState = field
            field = value
            updateState(oldState, value)
        }

    private fun updateState(oldState: LoadState, newState: LoadState) {
        if (oldState != newState) {
            if (oldState is LoadState.Complete) {
                notifyItemInserted(0)
            } else {
                when (newState) {
                    is LoadState.Complete -> notifyItemRemoved(0)
                    else -> notifyItemChanged(0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, position: Int) {
        holder.bind(state)
    }

    override fun getItemCount(): Int = when (state) {
        LoadState.Complete -> 0
        else -> 1
    }

    class LoadStateViewHolder(
        private val binding: RecyclerStateViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(state: LoadState) {
            if (state is LoadState.Error) {
                binding.errorMsg.text = state.message
            }
            binding.progressBar.isVisible = state is LoadState.Loading
            binding.btnRetry.isVisible = state is LoadState.Error
            binding.errorMsg.isVisible = state is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val binding = RecyclerStateViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoadStateViewHolder(binding, retry)
            }
        }
    }
}

sealed class LoadState {
    object Empty : LoadState()
    object Loading : LoadState()
    object Complete : LoadState()
    data class Error(val message: String) : LoadState()
}
