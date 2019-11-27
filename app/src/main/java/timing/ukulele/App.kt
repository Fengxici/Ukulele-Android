package timing.ukulele

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.IconicsSize
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import skin.support.utils.Slog

class App : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "timing.ukulele.ApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false) {
    override fun onCreate() {
        super.onCreate()
        initSkin()
        initDrawer()
        initBrowser()
    }

    /**
     * 初始化皮肤
     */
    private fun initSkin() {
        // 框架换肤日志打印
        Slog.DEBUG = true
        SkinCompatManager.withoutActivity(this)
                .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤
                .addInflater(SkinMaterialViewInflater()) // material design
                .addInflater(SkinConstraintViewInflater()) // ConstraintLayout
                .addInflater(SkinCardViewInflater()) // CardView v7
//                .setSkinStatusBarColorEnable(true) // 关闭状态栏换肤
//                .setSkinWindowBackgroundEnable(false)           // 关闭windowBackground换肤
//                .setSkinAllActivityEnable(false)                // true: 默认所有的Activity都换肤; false: 只有实现SkinCompatSupportable接口的Activity换肤
                .loadSkin()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    /**
     * 初始化侧滑菜单
     */
    private fun initDrawer() {
        //initialize and create the image loader logic
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                Glide.with(imageView.context).load(uri).placeholder(placeholder).into(imageView)
            }

            override fun cancel(imageView: ImageView) {
                Glide.with(imageView.context).clear(imageView)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                return when (tag) {
                    DrawerImageLoader.Tags.PROFILE.name -> DrawerUIUtils.getPlaceHolder(ctx)
                    DrawerImageLoader.Tags.ACCOUNT_HEADER.name -> IconicsDrawable(ctx).iconText(" ").backgroundColor(IconicsColor.colorRes(com.mikepenz.materialdrawer.R.color.primary)).size(IconicsSize.dp(56))
                    "customUrlItem" -> IconicsDrawable(ctx).iconText(" ").backgroundColor(IconicsColor.colorRes(R.color.md_red_500)).size(IconicsSize.dp(56))
                    //we use the default one for
                    //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()
                    else -> super.placeholder(ctx, tag)
                }
            }
        })
    }

    /**
     * 初始化浏览器内核
     */
    private fun initBrowser() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}