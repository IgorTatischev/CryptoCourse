package com.example.cryptocourse.listFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocourse.R
import com.example.cryptocourse.databinding.CoinItemBinding
import com.example.cryptocourse.model.CoinItem

class CoinListAdapter: RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    private var coinList = emptyList<CoinItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CoinItemBinding.bind(itemView)
        fun bind(item: CoinItem) {
            binding.apply {
                Glide.with(itemView.context).load(item.image).into(imageCoin)
                coinName.text = item.name
                coinShort.text = item.symbol.uppercase()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coinList[position])
    }

    override fun getItemCount(): Int {
        return  coinList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun update(coinList: List<CoinItem>){
        this.coinList = coinList
        notifyDataSetChanged()
    }
}

