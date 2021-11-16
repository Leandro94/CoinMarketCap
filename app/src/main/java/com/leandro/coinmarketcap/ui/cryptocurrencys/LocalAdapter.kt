package com.leandro.coinmarketcap.ui.cryptocurrencys

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
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import com.leandro.coinmarketcap.utils.formatDoubleToStringCurrency
import com.leandro.coinmarketcap.utils.formatDoubleToStringCurrencyWithSymbol
import com.leandro.coinmarketcap.utils.getProgressDrawable
import com.leandro.coinmarketcap.utils.loadImage

/**
 * Created by Leandro.Reis on 15/11/2021.
 */
class LocalAdapter(
    private val onClickListener: (view: View, cryptocurrency: Cryptocurrency, position: Int) -> Unit
) : ListAdapter<Cryptocurrency, LocalAdapter.LocalViewHolder>(LocalAdapter) {

    private val list = arrayListOf<Cryptocurrency>()

    private companion object : DiffUtil.ItemCallback<Cryptocurrency>() {
        override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val biding = AdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LocalViewHolder(biding, onClickListener)
    }

    override fun onBindViewHolder(holderLocal: LocalViewHolder, position: Int) {
        val cryptocurrency = list[position]
        holderLocal.viewBiding(cryptocurrency)
    }

    override fun getItemCount() = list.size

    inner class LocalViewHolder(
        private val biding: AdapterItemBinding,
        private val onClickListener: (view: View, cryptocurrency: Cryptocurrency, position: Int) -> Unit
    ) : RecyclerView.ViewHolder(biding.root) {
        fun viewBiding(cryptocurrency: Cryptocurrency) {
            biding.apply {
                formatterStyle(
                    cryptocurrency.quote.brl.percentChange1h,
                    tvValue1h,
                    ivPercent1h
                )
                tvPrice.text =
                    formatDoubleToStringCurrencyWithSymbol(cryptocurrency.quote.brl.price)
                tvName.text = cryptocurrency.name
                tvSymbol.text = cryptocurrency.symbol
                tvValue1h.text =
                    formatDoubleToStringCurrency(cryptocurrency.quote.brl.percentChange1h)
                ivCoin.loadImage(
                    cryptocurrency.imgUrl,
                    getProgressDrawable(biding.ivCoin.context)
                )

                itemView.setOnClickListener {
                    it.postDelayed({
                        onClickListener(it, cryptocurrency, layoutPosition)
                    }, 300)
                }
            }
        }
    }

    private fun formatterStyle(price: Double, txt_valor: TextView, imageView: ImageView) {
        if (price < 0) {
            txt_valor.setTextColor(Color.parseColor("#FF0000"))
            imageView.setImageResource(R.drawable.ic_trending_down_red_24)

        } else if (price == 0.00) {
            txt_valor.setTextColor(Color.parseColor("#C1C1C1"))
            imageView.setImageResource(R.drawable.ic_trending_flat_grey_24)
        } else {
            txt_valor.setTextColor(Color.parseColor("#32CD32"))
            imageView.setImageResource(R.drawable.ic_trending_up_green_24)
        }
    }

    /*override fun submitList(list: MutableList<Cryptocurrency>?) {
        super.submitList(list?.distinct())
        list?.let {
            this.list.addAll(it)
        }
    }*/

    fun clearList() {
        list.clear()
    }

    fun getList() = list

    fun removeItemList(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun modifyItemList(position: Int, cryptocurrency: Cryptocurrency) {
        list[position] = cryptocurrency
        notifyItemChanged(position)
    }
}