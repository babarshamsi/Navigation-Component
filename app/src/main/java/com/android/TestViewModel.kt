package com.android

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TestViewModel: ViewModel() {

    private val job =  SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.IO)}

    fun exampleMethod() {
        ioScope.launch {
            fetchData()
            withContext(Dispatchers.Main) {

            }
        }
    }

    private suspend fun fetchData() {

    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }


}