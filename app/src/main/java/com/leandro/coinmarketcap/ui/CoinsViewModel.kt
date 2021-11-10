package com.leandro.coinmarketcap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.data.model.Coin
import com.leandro.coinmarketcap.data.model.Data
import com.leandro.coinmarketcap.domain.usecase.GetRemoteListCoins
import com.leandro.coinmarketcap.domain.usecase.SaveListCoins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val getRemoteListCoins: GetRemoteListCoins,
    private val saveListCoins: SaveListCoins
) : ViewModel() {
    private val _getRemoteEvent = MutableLiveData<DataState<Data>?>()
    val getRemoteEvent: LiveData<DataState<Data>?>
        get() = _getRemoteEvent

    private val _getLocalEvent = MutableLiveData<DataState<List<Coin>>>()
    val getLocalEvent: LiveData<DataState<List<Coin>>>
        get() = _getLocalEvent

    private val _saveListEvent = MutableLiveData<DataState<List<Long>>>()
    val saveListEvent: LiveData<DataState<List<Long>>>
        get() = _saveListEvent

    fun getRemoteList() {
        viewModelScope.launch {
            _getRemoteEvent.value = getRemoteListCoins.invoke()
        }
    }

    fun insertAll() {
        viewModelScope.launch {
           // _saveListEvent.value = saveListCoins.invoke()
        }
    }
}