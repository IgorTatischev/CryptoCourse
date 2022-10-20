package com.example.cryptocourse.ui.coinListFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.CoinItemBinding
import com.example.cryptocourse.model.coins.CoinItem

class CoinListAdapter: RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    private var coinList = ArrayList<CoinItem>()
    private var isUsd = true

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CoinItemBinding.bind(itemView)
        fun bind(item: CoinItem, isUsd : Boolean) {
            binding.apply {
                Glide.with(itemView.context).load(item.image).into(imageCoin)
                coinName.text = item.name
                coinShort.text = item.symbol.uppercase()
                if (isUsd)
                    coinPrice.text =StringBuilder("$" + item.current_price.toString())
                else
                    coinPrice.text =StringBuilder("\u20ac" + item.current_price.toString())
                if (item.price_change_percentage_24h > 0){
                    coinIndex.text = StringBuilder("+" + item.price_change_percentage_24h.toString() + "%")
                    coinIndex.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_green_dark))
                }
                else{
                    coinIndex.text = StringBuilder(item.price_change_percentage_24h.toString() + "%")
                    coinIndex.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_red_dark))
                }
                itemView.setOnClickListener{
                    val bundle = Bundle()
                    bundle.putString("idCoin",item.id)
                    Navigation.findNavController(it).navigate(R.id.action_LirstFragment_to_DescriptionFragment,bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coinList[position],isUsd)
    }

    override fun getItemCount(): Int {
        return  coinList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(coinList: List<CoinItem>, isUsd: Boolean){
        this.coinList = coinList as ArrayList<CoinItem>
        this.isUsd = isUsd
        notifyDataSetChanged()
    }
}

