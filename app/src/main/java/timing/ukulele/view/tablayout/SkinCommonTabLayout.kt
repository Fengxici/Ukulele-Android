package timing.ukulele.view.tablayout

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.flyco.tablayout.CommonTabLayout
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import timing.ukulele.R

class SkinCommonTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CommonTabLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?
    private var mIndicatorColorResId = SkinCompatHelper.INVALID_ID
    private var mUnderlineColorResId = SkinCompatHelper.INVALID_ID
    private var mDividerColorResId = SkinCompatHelper.INVALID_ID
    private var mTextSelectColorResId = SkinCompatHelper.INVALID_ID
    private var mTextUnSelectColorResId = SkinCompatHelper.INVALID_ID
    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTabLayout)
        mIndicatorColorResId = ta.getResourceId(R.styleable.CommonTabLayout_tl_indicator_color, SkinCompatHelper.INVALID_ID)
        mIndicatorColorResId = SkinCompatHelper.checkResourceId(mIndicatorColorResId)
        mUnderlineColorResId = ta.getResourceId(R.styleable.CommonTabLayout_tl_underline_color, SkinCompatHelper.INVALID_ID)
        mUnderlineColorResId = SkinCompatHelper.checkResourceId(mUnderlineColorResId)
        mDividerColorResId = ta.getResourceId(R.styleable.CommonTabLayout_tl_divider_color, SkinCompatHelper.INVALID_ID)
        mDividerColorResId = SkinCompatHelper.checkResourceId(mDividerColorResId)
        mTextSelectColorResId = ta.getResourceId(R.styleable.CommonTabLayout_tl_textSelectColor, SkinCompatHelper.INVALID_ID)
        mTextSelectColorResId = SkinCompatHelper.checkResourceId(mTextSelectColorResId)
        mTextUnSelectColorResId = ta.getResourceId(R.styleable.CommonTabLayout_tl_textUnselectColor, SkinCompatHelper.INVALID_ID)
        mTextUnSelectColorResId = SkinCompatHelper.checkResourceId(mTextUnSelectColorResId)
        ta.recycle()
        applyCommonTabLayoutResources()
    }

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    private fun applyCommonTabLayoutResources() {
        if (mIndicatorColorResId != SkinCompatHelper.INVALID_ID) {
            indicatorColor = SkinCompatResources.getColor(context, mIndicatorColorResId)
        }
        if (mUnderlineColorResId != SkinCompatHelper.INVALID_ID) {
            underlineColor = SkinCompatResources.getColor(context, mUnderlineColorResId)
        }
        if (mDividerColorResId != SkinCompatHelper.INVALID_ID) {
            dividerColor = SkinCompatResources.getColor(context, mDividerColorResId)
        }
        if (mTextSelectColorResId != SkinCompatHelper.INVALID_ID) {
            textSelectColor = SkinCompatResources.getColor(context, mTextSelectColorResId)
        }
        if (mTextUnSelectColorResId != SkinCompatHelper.INVALID_ID) {
            textUnselectColor = SkinCompatResources.getColor(context, mTextUnSelectColorResId)
        }
    }

    override fun applySkin() {
        applyCommonTabLayoutResources()
        mBackgroundTintHelper?.applySkin()
    }

    init {
        obtainAttributes(context, attrs)
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
    }
}