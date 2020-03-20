package ru.tradernet.presentation.ui.mainList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.tradernet.R
import ru.tradernet.domain.model.TikerInfoModel

class TikersAdapter(
) : ListAdapter<TikerInfoModel, TikerViewHolder>(
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

    fun bind(tikerInfoModel: TikerInfoModel) {
//        name.text = repository.name
//        id.text = repository.id.toString()
//        description.text = repository.description
    }
}

object RepoDiff : DiffUtil.ItemCallback<TikerInfoModel>() {
    override fun areItemsTheSame(
        oldItem: TikerInfoModel,
        newItem: TikerInfoModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: TikerInfoModel, newItem: TikerInfoModel): Boolean {
        return oldItem == newItem
    }
}