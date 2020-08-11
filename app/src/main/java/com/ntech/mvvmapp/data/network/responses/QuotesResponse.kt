package com.ntech.mvvmapp.data.network.responses

import com.ntech.mvvmapp.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)