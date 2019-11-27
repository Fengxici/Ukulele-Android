package timing.ukulele.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import timing.ukulele.MainActivity
import timing.ukulele.R

class DataFragment private constructor(context: MainActivity) : Fragment(), SwitchFragmentCallback {
    private var mContext: MainActivity = context

    companion object {
        @Volatile
        var instance: DataFragment? = null

        fun getInstance(context: MainActivity): DataFragment {
            if (instance == null) {
                synchronized(DataFragment::class) {
                    if (instance == null) {
                        instance = DataFragment(context)
                    }
                }
            }
            return instance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initTitleBar()
        return inflater.inflate(R.layout.fragment_data, null)
    }

    override fun onResume() {
        super.onResume()
        fragmentSwitched()
    }

    private fun initTitleBar() {
    }

    override fun fragmentSwitched() {
    }
}