package com.leandro.coinmarketcap.ui.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandro.coinmarketcap.data.api.DataState
import com.leandro.coinmarketcap.domain.model.Coin
import com.leandro.coinmarketcap.domain.usecase.GetLocalListUseCase
import com.leandro.coinmarketcap.domain.usecase.GetRemoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Leandro.Reis on 09/11/2021.
 */
@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val getRemoteListUseCase: GetRemoteListUseCase,
    private val getLocalListUseCase: GetLocalListUseCase
) : ViewModel() {
    private val _getRemoteListEvent = MutableLiveData<DataState<List<Coin>>?>()
    val getRemoteListEvent: LiveData<DataState<List<Coin>>?>
        get() = _getRemoteListEvent

    private val _getLocalListEvent = MutableLiveData<DataState<List<Coin>?>?>()
    val getLocalListEvent: LiveData<DataState<List<Coin>?>?>
        get() = _getLocalListEvent

    init {
        getRemoteList()
    }

    fun getRemoteList() {
        viewModelScope.launch {
            _getRemoteListEvent.value = getRemoteListUseCase.invoke()
        }
    }

    fun getLocalList() {
        viewModelScope.launch {
            _getLocalListEvent.value = getLocalListUseCase.invoke()
        }
    }
}