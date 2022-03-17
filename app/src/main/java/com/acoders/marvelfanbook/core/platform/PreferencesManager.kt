package com.acoders.marvelfanbook.core.platform

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringDef
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Retention(AnnotationRetention.SOURCE)
@StringDef(PreferencesManager.KEYS.USERID, PreferencesManager.KEYS.TOKEN)
annotation class PreferencesKeys

class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(MARVEL_PREF, Context.MODE_PRIVATE)

    fun getString(@PreferencesKeys key: String, defaultValue: String? = null): String? = sharedPreferences.getString(key, defaultValue)
    fun getInt(@PreferencesKeys key: String, defaultValue: Int = 0): Int = sharedPreferences.getInt(key, defaultValue)
    fun getBoolean(@PreferencesKeys key: String, defaultValue: Boolean): Boolean = sharedPreferences.getBoolean(key, defaultValue)

    fun setString(@PreferencesKeys key: String, value: String? = null) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun setInt(@PreferencesKeys key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun setBoolean(@PreferencesKeys key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun containsKey(@PreferencesKeys key: String): Boolean = sharedPreferences.contains(key)

    fun removeValue(@PreferencesKeys key: String) {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }

    fun clear() {
        with(sharedPreferences.edit()) {
           clear()
            apply()
        }
    }

    interface KEYS{
        companion object{
            private const val PREFENCES_DEF = "acoders.marvelfanbook"
            const val USERID: String = "$PREFENCES_DEF.userId"
            const val TOKEN: String = "$PREFENCES_DEF.token"
        }
    }

    companion object {
        private const val MARVEL_PREF: String = "pref_marvel_session"
    }

}