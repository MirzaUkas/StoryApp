package com.mirz.storyapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.mirz.storyapp.R

class EditText : AppCompatEditText {
    private lateinit var editTextBackground: Drawable
    private lateinit var editTextErrorBackground: Drawable
    private var isError = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isError) editTextErrorBackground else editTextBackground
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            if (!TextUtils.isEmpty(text) && text.toString().length < 8 && compoundDrawables[DRAWABLE_RIGHT] != null) {
                error = resources.getString(R.string.password_minimum_character)
                isError = true
            } else {
                error = null
                isError = false
            }
        })
    }

    private fun init() {
        editTextBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text) as Drawable
        editTextErrorBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error) as Drawable
    }

    companion object {
        const val DRAWABLE_RIGHT = 2

    }
}