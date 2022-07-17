package com.github.higithub.app

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

/**
 * Description:
 * Created By willke on 2022/7/16 12:55 下午
 */
class HiGitHubApplication: Application() {

    companion object {
        private lateinit var instance: HiGitHubApplication
        fun get() = instance
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        setupLog()
    }

    private fun setupLog() {
        // Log all priorities in debug builds, no-op in release builds.
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}