package com.ntech.mvvmapp.ui.home.quotes

import com.ntech.mvvmapp.R
import com.ntech.mvvmapp.data.db.entities.Quote
import com.ntech.mvvmapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
): BindableItem<ItemQuoteBinding>() {
    override fun getLayout(): Int = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

}