package com.example.cryptocourse.ui.coinListFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocourse.databinding.CoinItemBinding
import com.example.cryptocourse.model.coins.CoinItem

class CoinListAdapter(private val listener : Listener) : ListAdapter<CoinItem, CoinListAdapter.ViewHolder>(ItemCallback), View.OnClickListener {

    private var isUsd = true

    override fun onClick(v: View) {
        val coin = v.tag as CoinItem
        listener.onItemClick(coin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CoinItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin,isUsd)
    }

    interface Listener{
        fun onItemClick(item: CoinItem)
    }

    class ViewHolder(private val binding: CoinItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CoinItem, isUsd : Boolean) {
            binding.apply {
                root.tag = item //pass item to tag for listener
                Glide.with(itemView.context).load(item.image).into(imageCoin)
                coinName.text = item.name
                coinShort.text = item.symbol.uppercase()
                if (isUsd)
                    coinPrice.text  = StringBuilder("$" + item.current_price.toString())
                else
                    coinPrice.text = StringBuilder("\u20ac" + item.current_price.toString())
                if (item.price_change_percentage_24h > 0){
                    coinIndex.text = StringBuilder("+" + item.price_change_percentage_24h.toString() + "%")
                    coinIndex.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_green_dark))
                }
                else{
                    coinIndex.text = StringBuilder(item.price_change_percentage_24h.toString() + "%")
                    coinIndex.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_red_dark))
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(isUsd: Boolean){
        this.isUsd = isUsd
        notifyDataSetChanged()
    }

    object ItemCallback : DiffUtil.ItemCallback<CoinItem>() {
        override fun areItemsTheSame(oldItem: CoinItem, newItem: CoinItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinItem, newItem: CoinItem): Boolean {
            return oldItem == newItem
        }
    }
}