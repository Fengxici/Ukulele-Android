package timing.ukulele.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import timing.ukulele.MainActivity
import timing.ukulele.R

class CommunicateFragment private constructor(context: MainActivity) : Fragment(), SwitchFragmentCallback {
    private var viewBinder: Unbinder? = null
    private var mContext: Activity = context

    companion object {
        @Volatile
        var instance: CommunicateFragment? = null

        fun getInstance(context: MainActivity): CommunicateFragment {
            if (instance == null) {
                synchronized(CommunicateFragment::class) {
                    if (instance == null) {
                        instance = CommunicateFragment(context)
                    }
                }
            }
            return instance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_communicate, null)
        viewBinder = ButterKnife.bind(this, view)
        initTitleBar()
        return view
    }

    override fun onResume() {
        super.onResume()
        fragmentSwitched()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinder?.unbind()
    }

    override fun fragmentSwitched() {
    }

    private fun initTitleBar() {
    }

}