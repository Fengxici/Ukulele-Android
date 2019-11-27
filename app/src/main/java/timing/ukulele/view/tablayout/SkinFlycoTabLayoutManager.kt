package timing.ukulele.view.tablayout

import android.content.Context
import skin.support.SkinCompatManager

class SkinFlycoTabLayoutManager private constructor(context: Context) {
    companion object {
        @Volatile
        var instance: SkinFlycoTabLayoutManager? = null
            private set

        fun init(context: Context): SkinFlycoTabLayoutManager? {
            if (instance == null) {
                synchronized(SkinFlycoTabLayoutManager::class.java) {
                    if (instance == null) {
                        instance = SkinFlycoTabLayoutManager(context)
                    }
                }
            }
            return instance
        }

    }

    init {
        SkinCompatManager.init(context).addInflater(SkinFlycoTabLayoutInflater())
    }
}