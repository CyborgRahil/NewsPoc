package com.rahil.newspoc.ui.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.rahil.newspoc.databinding.ViewErrorBinding

/**
 * Widget used to display an empty state to the user
 */
class ErrorView : RelativeLayout {

    var errorListener: ErrorListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this)
        binding.buttonTryAgain.setOnClickListener { errorListener?.onTryAgainClicked() }
    }

}