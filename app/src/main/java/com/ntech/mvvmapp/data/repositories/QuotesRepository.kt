package com.ntech.mvvmapp.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ntech.mvvmapp.data.db.AppDatabase
import com.ntech.mvvmapp.data.db.entities.Quote
import com.ntech.mvvmapp.data.network.MyApi
import com.ntech.mvvmapp.data.network.SafeApiRequest
import com.ntech.mvvmapp.data.preferences.PreferenceProvider
import com.ntech.mvvmapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

@RequiresApi(Build.VERSION_CODES.O)
class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefernceProvider: PreferenceProvider
): SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchQuotes() {

        val lastSavedAt = prefernceProvider.getLastSavedAt()

        if(lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefernceProvider.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}