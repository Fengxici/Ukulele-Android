package timing.ukulele.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import timing.ukulele.MainActivity
import timing.ukulele.R

class WorkFragment private constructor(context: MainActivity) : Fragment(), SwitchFragmentCallback {
    private var mContext: MainActivity = context

    companion object {
        @Volatile
        var instance: WorkFragment? = null

        fun getInstance(context: MainActivity): WorkFragment {
            if (instance == null) {
                synchronized(WorkFragment::class) {
                    if (instance == null) {
                        instance = WorkFragment(context)
                    }
                }
            }
            return instance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initTitleBar()
        return inflater.inflate(R.layout.fragment_work, null)
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