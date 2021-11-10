package com.leandro.coinmarketcap.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val coinsListAdapter = CoinsAdapter(arrayListOf())
    private val viewModel by viewModels<CoinsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRemoteList()
        initObservers()
        binding.rcCoinsFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsListAdapter
        }
        /* binding.buttonFirst.setOnClickListener {
             findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
         }*/
    }

    private fun initObservers() {
        viewModel.getRemoteEvent.observe(viewLifecycleOwner, { state ->
            if (state is DataState.OnSuccess) {
                val listCoins = state.data.data
                coinsListAdapter.updateCoinList(listCoins)
                Log.d("xxx", "lista $listCoins")
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.message_service_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}