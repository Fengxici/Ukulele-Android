package timing.ukulele.view.tablayout

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.flyco.tablayout.SegmentTabLayout
import skin.support.content.res.SkinCompatResources
import skin.support.widget.SkinCompatBackgroundHelper
import skin.support.widget.SkinCompatHelper
import skin.support.widget.SkinCompatSupportable
import timing.ukulele.R

class SkinSegmentTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SegmentTabLayout(context, attrs, defStyleAttr), SkinCompatSupportable {
    private val mBackgroundTintHelper: SkinCompatBackgroundHelper?
    private var mIndicatorColorResId = SkinCompatHelper.INVALID_ID
    private var mDividerColorResId = SkinCompatHelper.INVALID_ID
    private var mTextSelectColorResId = SkinCompatHelper.INVALID_ID
    private var mTextUnSelectColorResId = SkinCompatHelper.INVALID_ID
    private var mBarColorResId = SkinCompatHelper.INVALID_ID
    private var mBarStrokeColorResId = SkinCompatHelper.INVALID_ID
    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentTabLayout)
        mIndicatorColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_indicator_color, SkinCompatHelper.INVALID_ID)
        mIndicatorColorResId = SkinCompatHelper.checkResourceId(mIndicatorColorResId)
        mDividerColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_divider_color, mIndicatorColorResId)
        mDividerColorResId = SkinCompatHelper.checkResourceId(mDividerColorResId)
        mTextSelectColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_textSelectColor, SkinCompatHelper.INVALID_ID)
        mTextSelectColorResId = SkinCompatHelper.checkResourceId(mTextSelectColorResId)
        mTextUnSelectColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_textUnselectColor, mIndicatorColorResId)
        mTextUnSelectColorResId = SkinCompatHelper.checkResourceId(mTextUnSelectColorResId)
        mBarColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_bar_color, SkinCompatHelper.INVALID_ID)
        mBarColorResId = SkinCompatHelper.checkResourceId(mBarColorResId)
        mBarStrokeColorResId = ta.getResourceId(R.styleable.SegmentTabLayout_tl_bar_stroke_color, mIndicatorColorResId)
        mBarStrokeColorResId = SkinCompatHelper.checkResourceId(mBarStrokeColorResId)
        ta.recycle()
        applySegmentTabLayoutResources()
    }

    private fun applySegmentTabLayoutResources() {
        if (mIndicatorColorResId != SkinCompatHelper.INVALID_ID) {
            indicatorColor = SkinCompatResources.getColor(context, mIndicatorColorResId)
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
        if (mBarColorResId != SkinCompatHelper.INVALID_ID) {
            setBarColor(SkinCompatResources.getColor(context, mBarColorResId))
        }
        if (mBarStrokeColorResId != SkinCompatHelper.INVALID_ID) {
            setBarStrokeColor(SkinCompatResources.getColor(context, mBarStrokeColorResId))
        }
    }

    private fun setBarColor(color: Int) {
        try {
            val barColor = SegmentTabLayout::class.java.getDeclaredField("mBarColor")
            barColor.isAccessible = true
            barColor[this] = color
            invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setBarStrokeColor(color: Int) {
        try {
            val barStrokeColor = SegmentTabLayout::class.java.getDeclaredField("mBarStrokeColor")
            barStrokeColor.isAccessible = true
            barStrokeColor[this] = color
            invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun applySkin() {
        applySegmentTabLayoutResources()
        mBackgroundTintHelper?.applySkin()
    }

    init {
        obtainAttributes(context, attrs)
        mBackgroundTintHelper = SkinCompatBackgroundHelper(this)
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr)
    }
}