package timing.ukulele.view.tablayout

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.flyco.tablayout.R
import com.flyco.tablayout.widget.MsgView
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import skin.support.widget.SkinCompatTextHelper

class SkinMsgView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MsgView(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mTextHelper: SkinCompatTextHelper?
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?
    private var mBackgroundColorResId = SkinCompatHelper.INVALID_ID
    private var mStrokeColorResId = SkinCompatHelper.INVALID_ID
    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun setTextAppearance(resId: Int) {
        setTextAppearance(context, resId)
    }

    override fun setTextAppearance(context: Context, resId: Int) {
        super.setTextAppearance(context, resId)
        mTextHelper?.onSetTextAppearance(context, resId)
    }

    fun setBackgroundColorResource(resId: Int) {
        mBackgroundColorResId = resId
        applyBackgroundColorResource()
    }

    private fun applyBackgroundColorResource() {
        mBackgroundColorResId = SkinCompatHelper.checkResourceId(mBackgroundColorResId)
        if (mBackgroundColorResId != SkinCompatHelper.INVALID_ID) {
            backgroundColor = SkinCompatResources.getColor(context, mBackgroundColorResId)
        }
    }

    fun setStrokeColorResource(resId: Int) {
        mStrokeColorResId = resId
        applyStrokeColorResource()
    }

    private fun applyStrokeColorResource() {
        mStrokeColorResId = SkinCompatHelper.checkResourceId(mStrokeColorResId)
        if (mStrokeColorResId != SkinCompatHelper.INVALID_ID) {
            strokeColor = SkinCompatResources.getColor(context, mStrokeColorResId)
        }
    }

    override fun applySkin() {
        applyBackgroundColorResource()
        applyStrokeColorResource()
        mBackgroundTintHelper?.applySkin()
        mTextHelper?.applySkin()
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MsgView)
        mBackgroundColorResId = ta.getResourceId(R.styleable.MsgView_mv_backgroundColor, SkinCompatHelper.INVALID_ID)
        mStrokeColorResId = ta.getResourceId(R.styleable.MsgView_mv_strokeColor, SkinCompatHelper.INVALID_ID)
        applyBackgroundColorResource()
        applyStrokeColorResource()
        ta.recycle()
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
        mTextHelper = SkinCompatTextHelper.create(this)
        mTextHelper.loadFromAttributes(attrs, defStyleAttr)
    }
}