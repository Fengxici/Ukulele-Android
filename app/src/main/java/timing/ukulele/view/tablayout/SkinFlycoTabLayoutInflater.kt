package timing.ukulele.view.tablayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import skin.support.app.SkinLayoutInflater

class SkinFlycoTabLayoutInflater : SkinLayoutInflater {
    override fun createView(context: Context, name: String, attrs: AttributeSet): View {
        var view: View? = null
        when (name) {
            "com.flyco.tablayout.SlidingTabLayout" -> view = SkinSlidingTabLayout(context, attrs)
            "com.flyco.tablayout.CommonTabLayout" -> view = SkinCommonTabLayout(context, attrs)
            "com.flyco.tablayout.SegmentTabLayout" -> view = SkinSegmentTabLayout(context, attrs)
            "com.flyco.tablayout.widget.MsgView" -> view = SkinMsgView(context, attrs)
        }
        return view!!
    }
}