package krasnikov.project.pmfightacademy.app.pagination

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListAdapter<T, VH : RecyclerView.ViewHolder>(
    private val offsetStartLoadMore: Int = 4,
    diffCallback: DiffUtil.ItemCallback<T>,
    private val loadNextData: () -> Unit
) : ListAdapter<T, VH>(diffCallback) {

    var onItemClickListener: (item: T) -> Unit = {}

    final override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, getItem(position))
        holder.itemView.setOnClickListener { onItemClickListener(getItem(position)) }

        // start loading when reach certain position
        if (position == itemCount - offsetStartLoadMore) {
            loadNextData()
        }
    }

    abstract fun onBindViewHolder(holder: VH, item: T)
}


