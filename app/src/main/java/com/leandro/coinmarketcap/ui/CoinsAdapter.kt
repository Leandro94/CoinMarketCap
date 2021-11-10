package com.leandro.coinmarketcap.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.databinding.AdapterItemBinding
import com.leandro.coinmarketcap.utils.IMAGE_EXTENSION
import com.leandro.coinmarketcap.utils.IMAGE_URL
import com.leandro.coinmarketcap.utils.getProgressDrawable
import com.leandro.coinmarketcap.utils.loadImage

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
class CoinsAdapter(private val adapterList: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinsAdapter.ViewHolder>(), CoinClickListener {

    fun updateCoinList(list: List<Coin>?) {
        adapterList.clear()
        list?.let {
            adapterList.addAll(list)
        }
        notifyItemRangeChanged(0, adapterList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsAdapter.ViewHolder {
        val biding = AdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(biding)
    }

    override fun onBindViewHolder(holder: CoinsAdapter.ViewHolder, position: Int) {
        val coin = adapterList[position]
        holder.viewBiding(coin)
    }

    override fun getItemCount() = adapterList.size

    override fun onCoinClicked(v: View) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(
        private val biding: AdapterItemBinding,
    ) : RecyclerView.ViewHolder(biding.root) {

        fun viewBiding(coin: Coin) {
            biding.apply {
                tvName.text = coin.name
                tvSymbol.text = coin.symbol
                ivCoin.loadImage(IMAGE_URL + adapterList[adapterPosition].id + IMAGE_EXTENSION,
                    getProgressDrawable(biding.ivCoin.context))
            }
        }
    }
}

interface CoinClickListener {
    fun onCoinClicked(v: View)
}