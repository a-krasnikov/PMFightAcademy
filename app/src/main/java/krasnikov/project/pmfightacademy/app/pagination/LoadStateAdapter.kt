package krasnikov.project.pmfightacademy.app.pagination

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.view.LoadingErrorView

class LoadStateAdapter(private val retry: () -> Unit) :
    RecyclerView.Adapter<LoadStateAdapter.LoadStateViewHolder>() {

    private companion object {
        val mainHandler: Handler = Handler(Looper.getMainLooper())
    }

    var state: PaginationState = PaginationState.Empty
        set(value) {
            val oldState = field
            field = value
            updateState(oldState, value)
        }

    private fun updateState(oldState: PaginationState, newState: PaginationState) {
        if (oldState != newState) {
            if (oldState is PaginationState.Complete) {
                mainHandler.post { notifyItemInserted(0) }
            } else {
                when (newState) {
                    is PaginationState.Complete -> mainHandler.post { notifyItemRemoved(0) }
                    else -> mainHandler.post { notifyItemChanged(0) }
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
        PaginationState.Complete -> 0
        else -> 1
    }

    class LoadStateViewHolder(
        private val loadingErrorView: LoadingErrorView,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(loadingErrorView) {

        fun bind(state: PaginationState) {
            when (state) {
                is PaginationState.Empty -> {
                    loadingErrorView.resetState()
                }
                is PaginationState.Loading -> {
                    loadingErrorView.showLoading()
                }
                is PaginationState.Complete -> {
                    loadingErrorView.resetState()
                }
                is PaginationState.Error -> {
                    loadingErrorView.showError(state.error.errorType.errorStringRes, retry)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val view = LoadingErrorView(parent.context).apply {
                    layoutParams = params
                }
                return LoadStateViewHolder(view, retry)
            }
        }
    }
}
