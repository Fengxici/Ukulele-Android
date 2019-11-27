package timing.ukulele

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.IconicsSize
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import timing.ukulele.adapter.MainPageAdapter
import timing.ukulele.fragment.CommunicateFragment
import timing.ukulele.fragment.DataFragment
import timing.ukulele.fragment.NewsFragment
import timing.ukulele.fragment.WorkFragment
import timing.ukulele.view.tablayout.TabEntity

class MainActivity : AppCompatActivity() {
    private var viewBinder: Unbinder? = null
    @BindView(R.id.toolbar)
    lateinit var mToolbarContainer: LinearLayout
    @BindView(R.id.tab_layout)
    lateinit var mTabLayout: CommonTabLayout
    @BindView(R.id.frame_container)
    lateinit var mViewPager: ViewPager2


    private lateinit var result: Drawer
    private lateinit var profile: IProfile<*>
    private lateinit var headerResult: AccountHeader
    private lateinit var mPagerAdapter: MainPageAdapter

    private var mTabEntities = ArrayList<CustomTabEntity>(4)
    private var mFragments = ArrayList<Fragment>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinder = ButterKnife.bind(this)
        initTabLayout()
//        initProfile(savedInstanceState)
        // 注意为止，在dDrawerLayout.setDrawerListener(ActionBarDrawerToggle)之后才会生效
        //todo 原型头像
//        toolbar?.navigationIcon=ContextCompat.getDrawable(this,R.mipmap.profile3)
//        Glide.with(this).load(R.mipmap.profile3).apply(RequestOptions.bitmapTransform(CircleCrop())).into(headerView)
//        Glide.with(this).load(R.mipmap.profile3).into(headerView)
    }

    /**
     * 初始化侧滑菜单
     */
    private fun initProfile(savedInstanceState: Bundle?) {
        profile = ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(ContextCompat.getDrawable(this, R.mipmap.profile))
        buildHeader(savedInstanceState)
        result = DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        //todo 菜单项
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(object : Drawer.OnDrawerNavigationListener {
                    override fun onNavigationClickListener(clickedView: View): Boolean {
                        this@MainActivity.finish()
                        return true
                    }
                })
                .addStickyDrawerItems(
                        SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )
                .withSavedInstance(savedInstanceState)
                .build()
    }

    /**
     * 初始化tab布局
     */
    private fun initTabLayout() {
        val titles = arrayOf(resources?.getString(R.string.tab_news), resources?.getString(R.string.tab_work), resources?.getString(R.string.tab_data), resources?.getString(R.string.tab_communicate))
        val iconUnSelectIds = arrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
                R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect)
        val iconSelectIds = arrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
                R.mipmap.tab_contact_select, R.mipmap.tab_more_select)
        for (i in titles.indices) {
            mTabEntities.add(TabEntity(titles[i], iconSelectIds[i], iconUnSelectIds[i]))
        }
        //注意顺序要和前面标题一致
        mTabLayout.setTabData(mTabEntities)
        mFragments.add(NewsFragment.getInstance(this))
        mFragments.add(WorkFragment.getInstance(this))
        mFragments.add(DataFragment.getInstance(this))
        mFragments.add(CommunicateFragment.getInstance(this))

        mPagerAdapter = MainPageAdapter(supportFragmentManager, lifecycle, mFragments)
        mViewPager.adapter = mPagerAdapter

        mTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                mViewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })

        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mTabLayout.currentTab = position
            }
        })
        mViewPager.currentItem = 0
    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     * @param savedInstanceState
     */
    private fun buildHeader(savedInstanceState: Bundle?) {
        // Create the AccountHeader
        headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.header)
                .withCompactStyle(true)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        ProfileSettingDrawerItem().withName("添加账号").withDescription("新建一个Ukulele账号")
                                .withIcon(IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().padding(IconicsSize.dp(5))
                                ).withIdentifier(PROFILE_SETTING.toLong()),
                        ProfileSettingDrawerItem().withName("管理账号").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withTextColor(ContextCompat.getColor(this, R.color.material_drawer_dark_primary_text))
                .withOnAccountHeaderListener(object : AccountHeader.OnAccountHeaderListener {
                    override fun onProfileChanged(view: View?, profile: IProfile<*>, current: Boolean): Boolean {
                        if (profile is IDrawerItem<*> && (profile as IDrawerItem<*>).identifier == PROFILE_SETTING.toLong()) {
                            val newProfile = ProfileDrawerItem().withNameShown(true).withName("Batman")
                                    .withEmail("batman@gmail.com").withIcon(ContextCompat.getDrawable(this@MainActivity, R.mipmap.profile5))

                            val profiles = headerResult.profiles
                            if (profiles != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, profiles.size - 2)
                            } else {
                                headerResult.addProfiles(newProfile)
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build()
    }

    override fun onSaveInstanceState(_outState: Bundle) {
        var outState = _outState
        //add the values which need to be saved from the drawer to the bundle
        if (::result.isInitialized) {
            outState = result.saveInstanceState(outState)
        }
        //add the values which need to be saved from the accountHeader to the bundle
        if (::headerResult.isInitialized) {
            outState = headerResult.saveInstanceState(outState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinder!!.unbind()
    }

    companion object {
        private const val PROFILE_SETTING = 1
    }
}