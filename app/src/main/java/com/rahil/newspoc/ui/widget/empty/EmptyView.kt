package com.rahil.newspoc.ui.widget.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.rahil.newspoc.databinding.ViewEmptyBinding

/**
 * Widget used to display an empty state to the user
 */
class EmptyView: RelativeLayout {

    var emptyListener: EmptyListener? = null

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
        val binding = ViewEmptyBinding.inflate(LayoutInflater.from(context), this)
        binding.buttonCheckAgain.setOnClickListener { emptyListener?.onCheckAgainClicked() }
    }

}