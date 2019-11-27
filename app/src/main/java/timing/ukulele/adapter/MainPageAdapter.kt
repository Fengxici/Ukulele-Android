package timing.ukulele.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, pages: ArrayList<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private var mPages: ArrayList<Fragment> = pages

    override fun getItemCount(): Int {
        return this.mPages.size
    }

    override fun createFragment(position: Int): Fragment {
        return mPages.get(position)
    }


}
