package com.leandro.coinmarketcap.ui.cryptocurrencys

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.databinding.FragmentCryptocurrencyBinding
import com.leandro.coinmarketcap.domain.model.Cryptocurrency
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CryptocurrencyFragment : Fragment() {
    private var _binding: FragmentCryptocurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CryptocurrencyViewModel>()
    private val searchList = arrayListOf<Cryptocurrency>()
    private val adapterItemsList = arrayListOf<Cryptocurrency>()

    private val localAdapter by lazy {
        LocalAdapter { view, cryptocurrency, position ->
            when (view.id) {
                R.id.cv_item -> {
                    val directions =
                        CryptocurrencyFragmentDirections.actionCryptocurrencyToDetail(cryptocurrency)
                    findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptocurrencyBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewActionsRequest()
        binding.rcvCoinsFragment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = localAdapter
        }
        setListeners()
        initObservers()
    }

    private fun setListeners() {
        with(binding) {
            srlRefresh.setOnRefreshListener {
                viewActionsRequest()
                viewModel.getRemoteList()
            }
        }
    }

    private fun viewActionsRequest() {
        binding.rcvCoinsFragment.visibility = View.GONE
        binding.pgbRequest.visibility = View.VISIBLE
        binding.srlRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView
            searchView.queryHint = getString(R.string.action_search)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty() && !adapterItemsList.isNullOrEmpty()) {
                        searchList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        adapterItemsList.forEach {
                            if (it.name.lowercase(Locale.getDefault()).contains(search)) {
                                searchList.add(it)
                                localAdapter.clearList()
                                localAdapter.submitList(searchList)
                            }
                        }
                    } else {
                        localAdapter.clearList()
                        localAdapter.submitList(adapterItemsList)
                    }
                    return true
                }
            })
        }
    }

    private fun initObservers() {
        viewModel.getRemoteListEvent.observe(viewLifecycleOwner, { state ->
            if (state is DataState.OnError) {
                Toast.makeText(
                    activity,
                    getString(R.string.message_service_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            viewModel.getLocalList()
        })

        viewModel.getLocalListEvent.observe(viewLifecycleOwner, { state ->
            if (state is DataState.OnSuccess) {
                updateListRecyclerView(state.data)
            } else if (state is DataState.OnException) {
                Toast.makeText(
                    activity,
                    getString(R.string.message_database_exception),
                    Toast.LENGTH_LONG
                ).show()
                Log.d("xxx", "erro "+state.e.message)
            }
            binding.pgbRequest.visibility = View.GONE
        })
    }

    private fun updateListRecyclerView(list: List<Cryptocurrency>?) {
        if (list.isNullOrEmpty()) {
            Toast.makeText(
                activity,
                getString(R.string.message_database_empty),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        binding.rcvCoinsFragment.visibility = View.VISIBLE
        localAdapter.clearList()
        localAdapter.submitList(list as MutableList<Cryptocurrency>?)
        adapterItemsList.clear()
        adapterItemsList.addAll(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}