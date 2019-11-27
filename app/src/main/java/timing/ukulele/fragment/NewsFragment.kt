package timing.ukulele.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.flyco.tablayout.SegmentTabLayout
import timing.ukulele.MainActivity
import timing.ukulele.R

class NewsFragment private constructor(context: MainActivity) : Fragment(), SwitchFragmentCallback {
    private var viewBinder: Unbinder? = null
    private var mContext: MainActivity = context

    companion object {
        @Volatile
        var instance: NewsFragment? = null

        fun getInstance(context: MainActivity): NewsFragment {
            if (instance == null) {
                synchronized(NewsFragment::class) {
                    if (instance == null) {
                        instance = NewsFragment(context)
                    }
                }
            }
            return instance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, null)
        viewBinder = ButterKnife.bind(this, view)
        initTitleBar()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinder?.unbind()
    }

    override fun onResume() {
        super.onResume()
        fragmentSwitched()
    }

    override fun fragmentSwitched() {
    }

    private fun initTitleBar() {
    }

}