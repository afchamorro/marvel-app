package com.acoders.marvelfanbook.core.platform

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesManager @Inject constructor(@ApplicationContext private val context: Context)  {

    private val resources: Resources = context.resources
    private val packageName: String = context.packageName

     fun getString(stringResId: Int): String {
        return resources.getString(stringResId)
    }

     fun getString(stringResId: Int, vararg formatArgs: Any?): String {
        return resources.getString(stringResId, formatArgs)
    }

     fun getStringByLabel(label: String): String? {
        val stringResId = resources.getIdentifier(label, "string", packageName)
        return if (stringResId > 0)
            resources.getString(stringResId)
        else null
    }

     fun getDrawable(drawablreResId: Int): Drawable? {
        return ContextCompat.getDrawable(context,drawablreResId)
    }

     fun getColor(colorResId: Int): Int {
        return ContextCompat.getColor(context,colorResId)
    }

}