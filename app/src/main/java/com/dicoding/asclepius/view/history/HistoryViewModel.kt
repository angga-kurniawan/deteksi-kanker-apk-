package com.dicoding.asclepius.view.history

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.HistoryEntity
import com.dicoding.asclepius.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application = Application()) : ViewModel() {
    private val historyRepository = HistoryRepository(application)
    var historyList: MutableLiveData<List<HistoryEntity>> = MutableLiveData()

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    init {
        historyList.value = getHistory()
    }

    fun setCurrentImageUri(uri: Uri) {
        _currentImageUri.value = uri
    }

    fun addHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.addHistory(historyEntity)
        }
    }

    private fun getHistory(): List<HistoryEntity> = historyRepository.getHistory()
}