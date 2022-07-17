package com.github.higithub.utils

import androidx.appcompat.app.AppCompatActivity
import com.github.higithub.app.HiGitHubApplication

/**
 * Description:
 * Created By willke on 2022/7/16 6:20 下午
 */
object Sp {

    private val prefs by lazy {
        HiGitHubApplication.get().getSharedPreferences("app_prefs", AppCompatActivity.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        val edit = prefs.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun get(key: String, defaultValue: String): String? {
        return prefs.getString(key, defaultValue)
    }



}