package com.leandro.coinmarketcap.ui.coins

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.databinding.AdapterItemBinding
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.utils.formatDoubleToStringCurrency
import com.leandro.coinmarketcap.utils.formatDoubleToStringCurrencyWithSymbol
import com.leandro.coinmarketcap.utils.getProgressDrawable
import com.leandro.coinmarketcap.utils.loadImage

/**
 * Created by Leandro.Reis on 15/11/2021.
 */
class CoinsAdapter(
    private val onClickListener: (view: View, coin: Coin, position: Int) -> Unit
) : ListAdapter<Coin, CoinsAdapter.CoinViewHolder>(CoinsAdapter) {

    private val list = arrayListOf<Coin>()

    private companion object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val biding = AdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CoinViewHolder(biding, onClickListener)
    }

    override fun onBindViewHolder(holderCoin: CoinViewHolder, position: Int) {
        val coin = list[position]
        holderCoin.viewBiding(coin)
    }

    override fun getItemCount() = list.size

    inner class CoinViewHolder(
        private val biding: AdapterItemBinding,
        private val onClickListener: (view: View, coin: Coin, position: Int) -> Unit
    ) : RecyclerView.ViewHolder(biding.root) {
        fun viewBiding(coin: Coin) {
            biding.apply {
                setStyleColors(
                    coin.quote.brl.percentChange1h,
                    tvValue1h,
                    ivPercent1h
                )
                tvPrice.text =
                    formatDoubleToStringCurrencyWithSymbol(coin.quote.brl.price)
                tvName.text = coin.name
                tvSymbol.text = coin.symbol
                tvValue1h.text =
                    formatDoubleToStringCurrency(coin.quote.brl.percentChange1h)
                ivCoin.loadImage(
                    coin.imgUrl,
                    getProgressDrawable(biding.ivCoin.context)
                )

                itemView.setOnClickListener {
                    it.postDelayed({
                        onClickListener(it, coin, layoutPosition)
                    }, 300)
                }
            }
        }
    }

    private fun setStyleColors(price: Double, txt_valor: TextView, imageView: ImageView) {
        when {
            price < 0 -> {
                txt_valor.setTextColor(Color.parseColor("#FF0000"))
                imageView.setImageResource(R.drawable.ic_trending_down_red_24)
            }
            price == 0.00 -> {
                txt_valor.setTextColor(Color.parseColor("#C1C1C1"))
                imageView.setImageResource(R.drawable.ic_trending_flat_grey_24)
            }
            else -> {
                txt_valor.setTextColor(Color.parseColor("#32CD32"))
                imageView.setImageResource(R.drawable.ic_trending_up_green_24)
            }
        }
    }

    override fun submitList(list: MutableList<Coin>?) {
        super.submitList(list?.distinct())
        list?.let {
            this.list.addAll(it)
        }
    }

    fun submitListTest(list: MutableList<Coin>) {
        this.submitList(list)
    }

    fun clearList() {
        list.clear()
    }

    fun getList() = list

    fun removeItemList(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun modifyItemList(position: Int, coin: Coin) {
        list[position] = coin
        notifyItemChanged(position)
    }
}