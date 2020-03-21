package ru.tradernet.presentation.ui.mainList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.tradernet.R
import ru.tradernet.domain.model.TickerInfoModel

class TikersAdapter(
) : ListAdapter<TickerInfoModel, TikerViewHolder>(
    RepoDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TikerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TikerViewHolder(
            inflater.inflate(R.layout.item_tiker, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TikerViewHolder, position: Int) {
        val message = getItem(position)
        message?.let {
            holder.bind(it)
        }
    }
}

class TikerViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

//    private val name: TextView = itemView.findViewById(R.id.repoName)
//    private val id: TextView = itemView.findViewById(R.id.repoId)
//    private val description: TextView = itemView.findViewById(R.id.description)

    fun bind(tickerInfoModel: TickerInfoModel) {
//        name.text = repository.name
//        id.text = repository.id.toString()
//        description.text = repository.description
    }
}

object RepoDiff : DiffUtil.ItemCallback<TickerInfoModel>() {
    override fun areItemsTheSame(
        oldItem: TickerInfoModel,
        newItem: TickerInfoModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: TickerInfoModel, newItem: TickerInfoModel): Boolean {
        return oldItem == newItem
    }
}