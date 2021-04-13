package com.task.noteapp.features.notes.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.task.noteapp.R
import com.task.noteapp.databinding.EmptyStateViewBinding

typealias ActionButtonClickListener = () -> Unit

class EmptyStateView : LinearLayout {

    private var binding: EmptyStateViewBinding

    private var buttonClickListener: ActionButtonClickListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = EmptyStateViewBinding.inflate(inflater, this, true)

        val attributes: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.EmptyStateView, 0, 0)

        val emptyStateTitle: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateTitleText)
        val emptyStateImageSrc: Drawable? =
            attributes.getDrawable(R.styleable.EmptyStateView_emptyStateImageSrc)
        val emptyStateCaption: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateCaptionText)
        val emptyStateButtonText: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateButtonText)

        val emptyStateButtonVisible: Boolean =
            attributes.getBoolean(R.styleable.EmptyStateView_isButtonVisible, false)
        val emptyStateTitleVisible: Boolean =
            attributes.getBoolean(R.styleable.EmptyStateView_isTitleVisible, true)

        attributes.recycle()

        setTitle(emptyStateTitle)

        binding.title.isVisible = emptyStateTitleVisible

        setImage(emptyStateImageSrc)

        setCaption(emptyStateCaption)

        if (emptyStateButtonText != null) {
            binding.retryBtn.text = emptyStateButtonText
        }

        binding.retryBtn.isVisible = emptyStateButtonVisible

        binding.retryBtn.setOnClickListener {
            buttonClickListener?.invoke()
        }

        val actionBtnBounceAnim: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.image, "translationY", 0f, 25f, 0f)
        actionBtnBounceAnim.interpolator = AccelerateDecelerateInterpolator()
        actionBtnBounceAnim.duration = 3000
        actionBtnBounceAnim.repeatMode = ValueAnimator.RESTART
        actionBtnBounceAnim.repeatCount = ValueAnimator.INFINITE
        actionBtnBounceAnim.start()
    }

    fun setImage(emptyStateImageSrc: Drawable?) {
        if (emptyStateImageSrc != null) {
            binding.image.isVisible = true
            binding.image.setImageDrawable(emptyStateImageSrc)
        } else {
            binding.image.isVisible = false
        }
    }

    var isButtonVisible: Boolean = false
        set(value) {
            field = value
            binding.retryBtn.isVisible = value
        }

    fun setCaption(emptyStateCaption: String?) {
        if (emptyStateCaption != null) {
            binding.caption.text = emptyStateCaption
        }
    }

    fun setTitle(emptyStateTitle: String?) {
        if (emptyStateTitle != null) {
            binding.title.text = emptyStateTitle
        }
    }

    fun onRetry(action: ActionButtonClickListener) {
        buttonClickListener = action
    }
}