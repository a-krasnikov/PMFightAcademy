package krasnikov.project.pmfightacademy.app.pagination

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import krasnikov.project.pmfightacademy.databinding.RecyclerStateViewBinding

abstract class PaginationAdapter<T, VH : RecyclerView.ViewHolder>(
    private val offsetStartLoadMore: Int = 4,
    private val loadNextData: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentList = emptyList<T>()
        set(value) {
            val startPosition = currentList.size
            val countNewItems = value.size - startPosition
            if (countNewItems != 0) {
                field = value
                mainHandler.post { notifyItemRangeChanged(startPosition, countNewItems) }
            }
        }

    private var currentState: PaginationState = PaginationState.Loading
        set(value) {
            field = value
            mainHandler.post { notifyItemChanged(itemCount - 1) }
        }

    var onItemClickListener: (item: T) -> Unit = {}

    // list size + LoadStateViewHolder
    final override fun getItemCount(): Int = currentList.size + 1

    final override fun getItemViewType(position: Int): Int = when (position) {
        itemCount - 1 -> VIEW_TYPE_STATE
        else -> VIEW_TYPE_DATA
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_STATE -> LoadStateViewHolder.create(parent) { loadNextData() }
            else -> onCreateViewHolder(parent)
        }
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadStateViewHolder -> {
                holder.bind(currentState)
            }
            else -> {
                onBindViewHolder(holder as VH, getItem(position))
                holder.itemView.setOnClickListener { onItemClickListener(getItem(position)) }
            }
        }

        // start loading when reach certain position
        if (position == itemCount - offsetStartLoadMore - 2) {
            loadNextData()
        }
    }

    fun getItem(position: Int) = currentList[position]

    fun submitData(data: PaginationData<T>) {
        currentState = data.currentState
        currentList = data.availableData
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH, item: T)

    private companion object {
        const val VIEW_TYPE_STATE = 0
        const val VIEW_TYPE_DATA = 1

        val mainHandler: Handler = Handler(Looper.getMainLooper())
    }

    class LoadStateViewHolder(
        private val binding: RecyclerStateViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(state: PaginationState) {
            if (state is PaginationState.Error) {
                binding.errorMsg.text = itemView.resources.getString(state.error.errorType.errorStringRes)
            }
            binding.progressBar.isVisible = state is PaginationState.Loading
            binding.btnRetry.isVisible = state is PaginationState.Error
            binding.errorMsg.isVisible = state is PaginationState.Error
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

