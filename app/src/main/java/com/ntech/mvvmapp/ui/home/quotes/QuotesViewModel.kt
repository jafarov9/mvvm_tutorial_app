package com.ntech.mvvmapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.ntech.mvvmapp.data.repositories.QuotesRepository
import com.ntech.mvvmapp.util.lazyDeferred

class QuotesViewModel(
    quotesRepository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        quotesRepository.getQuotes()
    }
}