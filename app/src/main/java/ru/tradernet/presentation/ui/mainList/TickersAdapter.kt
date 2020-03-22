package ru.tradernet.presentation.ui.mainList

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.tradernet.R
import ru.tradernet.domain.model.TickerInfoModel

class TickersAdapter(
    private val listener: (TickerInfoModel) -> Unit
) : ListAdapter<TickerInfoModel, RepoViewHolder>(TickerDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(
            inflater.inflate(R.layout.item_ticker, parent, false)
        ) { position ->
            listener.invoke(getItem(position)!!)
        }
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val message = getItem(position)
        message?.let {
            holder.bind(it)
        }
    }
}

class RepoViewHolder(
    view: View,
    private val listener: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val tickerName: TextView = itemView.findViewById(R.id.tickerName)
    private val tickerCompany: TextView = itemView.findViewById(R.id.tickerCompany)
    private val changesPercent: TextView = itemView.findViewById(R.id.changesPercent)
    private val changesPoints: TextView = itemView.findViewById(R.id.changesPoints)
    private val tickerIcon: ImageView = itemView.findViewById(R.id.tickerIcon)

    init {
        itemView.setOnClickListener { listener.invoke(adapterPosition) }
    }

    fun bind(ticker: TickerInfoModel) {
        tickerName.text = ticker.name

        changesPercent.text = ticker.percentChanges?.toString()

        val imageLink =
            "https://tradernet.ru/logos/get-logo-by-ticker?ticker=" + ticker.name.toLowerCase()
        Glide.with(itemView)
            .load(imageLink)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    tickerIcon.visibility = View.GONE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    tickerIcon.visibility =
                        if (resource is GifDrawable?) View.GONE else View.VISIBLE
                    return false
                }

            })
            .into(tickerIcon)
    }
}

object TickerDiff : DiffUtil.ItemCallback<TickerInfoModel>() {
    override fun areItemsTheSame(
        oldItem: TickerInfoModel,
        newItem: TickerInfoModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: TickerInfoModel, newItem: TickerInfoModel): Boolean {
        return oldItem.name == newItem.name
                && oldItem.exchangeName == newItem.exchangeName
                && oldItem.percentChanges == newItem.percentChanges
                && oldItem.pointChanges == newItem.pointChanges
                && oldItem.stockName == newItem.stockName
                && oldItem.price == newItem.price
    }
}