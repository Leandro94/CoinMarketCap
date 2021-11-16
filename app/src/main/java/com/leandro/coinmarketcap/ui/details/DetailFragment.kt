package com.leandro.coinmarketcap.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.databinding.FragmentDetailBinding
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.utils.formatDoubleToStringCurrencyWithSymbol
import com.leandro.coinmarketcap.utils.getProgressDrawable
import com.leandro.coinmarketcap.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var coin: Coin
    private val entries = ArrayList<BarEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.detailsArgs?.let { coin = it }

        if (!this::coin.isInitialized) {
            findNavController().popBackStack()
            return
        }
        configBarData()
        configBarChartStyle()
        setInfosLayout()
    }

    private fun setInfosLayout() {
        entries.add(BarEntry(1f, coin.quote.brl.percentChange1h.toFloat()))
        entries.add(BarEntry(2f, coin.quote.brl.percentChange24h.toFloat()))
        entries.add(BarEntry(3f, coin.quote.brl.percentChange7d.toFloat()))
        configBarData()

        with(binding) {
            tvSymbol.text = coin.symbol
            tvCoinName.text = coin.name
            tvValueMarketCapDiluted.text =
                formatDoubleToStringCurrencyWithSymbol(coin.quote.brl.dilutedMarketCap)
            tvPrice.text = formatDoubleToStringCurrencyWithSymbol(coin.quote.brl.price)
            tvValueVolume24h.text =
                formatDoubleToStringCurrencyWithSymbol(coin.quote.brl.volume24h)
            tvValueMarketCap.text =
                formatDoubleToStringCurrencyWithSymbol(coin.quote.brl.marketCap)
            coin.quote.brl.marketCap
            tvValueCirculatingSupply.text =
                coin.circulatingSupply
            context?.let {
                ivCoin.loadImage(
                    coin.imgUrl,
                    getProgressDrawable(it)
                )
            }
        }
    }

    private fun setChartData(): ArrayList<BarEntry> {
        return entries
    }

    private fun configBarData() {
        val barDataSet = BarDataSet(setChartData(), "data")
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.textColor)
        barDataSet.valueTextSize = 16f
        barDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorWhite)

        val barData = BarData(barDataSet)
        with(binding) {
            barChart.data = barData
            barChart.notifyDataSetChanged()
            barChart.invalidate()
        }
    }

    private fun configBarChartStyle() {
        val xAxisLabel: ArrayList<String> = ArrayList()
        xAxisLabel.add("")
        xAxisLabel.add("1 Hora [%]")
        xAxisLabel.add("24 Horas [%]")
        xAxisLabel.add("7 Dias [%]")
        with(binding) {
            barChart.description.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.animate()
            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
            barChart.xAxis.textSize = 11f
            barChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)

            barChart.xAxis.axisLineColor =
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            barChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.textColor)
            barChart.xAxis.gridColor =
                ContextCompat.getColor(requireContext(), R.color.colorLineChart)

            barChart.axisLeft.textColor =
                ContextCompat.getColor(requireContext(), R.color.textColor)
            barChart.axisLeft.textColor =
                ContextCompat.getColor(requireContext(), R.color.colorLineChart)
            barChart.axisLeft.textSize = 14f
            barChart.axisLeft.gridColor =
                ContextCompat.getColor(requireContext(), R.color.textColor)

            barChart.axisRight.axisLineColor =
                ContextCompat.getColor(requireContext(), R.color.colorLineChart)
            barChart.axisRight.textColor =
                ContextCompat.getColor(requireContext(), R.color.textColor)
            barChart.axisRight.textSize = 14f
            barChart.axisRight.gridColor =
                ContextCompat.getColor(requireContext(), R.color.colorLineChart)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}