package com.leandro.coinmarketcap.ui.coins

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.coinmarketcap.R
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.databinding.FragmentCoinsBinding
import com.leandro.coinmarketcap.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CoinsFragment : Fragment() {
    private var _binding: FragmentCoinsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CoinsViewModel>()
    private val searchList = arrayListOf<Coin>()
    private val adapterItemsList = arrayListOf<Coin>()

    val coinAdapter by lazy {
        CoinsAdapter { view, cryptocurrency, position ->
            when (view.id) {
                R.id.cv_item -> {
                    val directions =
                        CoinsFragmentDirections.actionCoinsToDetail(cryptocurrency)
                    findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewActionsStartRequest()
        binding.rcvCoins.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = coinAdapter
        }
        setListeners()
        initObservers()
    }

    private fun setListeners() {
        with(binding) {
            srlRefresh.setOnRefreshListener {
                binding.rcvCoins.visibility = View.GONE
                viewActionsStartRequest()
                srlRefresh.postDelayed({ viewModel.getRemoteList() }, 500)
            }
            screenError.btnErrorRefresh.setOnClickListener {
                viewActionsStartRequest()
                it.postDelayed({ viewModel.getRemoteList() }, 500)
            }
        }
    }

    private fun viewActionsStartRequest() {
        binding.screenError.root.visibility = View.GONE
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
                                coinAdapter.clearList()
                                coinAdapter.submitList(searchList)
                            }
                        }
                    } else {
                        coinAdapter.clearList()
                        coinAdapter.submitList(adapterItemsList)
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
                binding.screenError.root.visibility = View.VISIBLE
            }
            binding.pgbRequest.visibility = View.GONE
        })
    }

    private fun updateListRecyclerView(list: List<Coin>?) {
        if (list.isNullOrEmpty()) {
            (activity as AppCompatActivity?)?.supportActionBar?.hide()
            viewActionsDatabaseEmpty()
            return
        }
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        checkVisibilityViews()
        coinAdapter.clearList()
        coinAdapter.submitList(list as MutableList<Coin>?)

        adapterItemsList.clear()
        adapterItemsList.addAll(list)
    }

    private fun viewActionsDatabaseEmpty() {
        binding.pgbRequest.visibility = View.GONE
        binding.screenError.root.visibility = View.VISIBLE
    }

    private fun checkVisibilityViews() {
        if (binding.screenError.root.visibility != View.GONE) {
            binding.screenError.root.visibility = View.GONE
        }
        if (binding.rcvCoins.visibility != View.VISIBLE) {
            binding.rcvCoins.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}