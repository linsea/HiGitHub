package com.github.higithub.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.github.higithub.app.*
import com.github.higithub.event.LogEvent
import com.github.higithub.network.ApiService
import com.github.higithub.utils.Sp
import logcat.logcat
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.random.Random
import kotlin.random.nextULong

/**
 * Description:
 * Created By willke on 2022/7/16 4:24 下午
 */
object AuthManager {

    private val TAG = "Login"

    const val AUTH_URL = "https://github.com/login/oauth/authorize"
    const val TOKEN_OAUTH = "https://github.com/login/oauth/access_token"

    private const val scope = "user public_repo"

    private var token = Sp.get("access_token", "")!!

    val tokenProvider: () -> String = { token }

    fun checkLogin() {
        EventBus.getDefault().postSticky(LogEvent(hasLogined()))
    }

    fun hasLogined() = token.isNotBlank()

    fun startGithubWebAuth(context: Context) {
        val url = makeAuthorizeUrl()
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    fun isOauthCallback(i: Intent?): Boolean {
        val uri = i?.data ?: return false
        logcat { "intent.data: $uri"}
        return uri.toString().lowercase(Locale.getDefault()).startsWith(OAUTH_SCHEME)
    }

    fun isGotCodeSuccess(i: Intent?): Boolean {
        val uri = i?.data ?: return false
        return !TextUtils.isEmpty(uri.getQueryParameter("code"))
    }

    fun getCode(i: Intent?): String {
        val uri = i?.data!!
        return uri.getQueryParameter("code")!!
    }

    fun getOauthErrorDesc(i: Intent): String? {
        if (isGotCodeSuccess(i)) {
            return null
        }
        val uri = i.data ?: return null
        val error = uri.getQueryParameter("error")
        val errorDesc = uri.getQueryParameter("error_description")
        if (!error.isNullOrBlank() || !errorDesc.isNullOrBlank()) {
            return "$error $errorDesc"
        }
        return null
    }

    suspend fun reqAccessToken(code: String) {
        val token = ApiService.getAccessToken(TOKEN_OAUTH, GITHUB_ID, GITHUB_SECRET, code)
        onLoginSuccess(token.access_token)
    }

    private fun onLoginSuccess(token: String) {
        this.token = token
        Sp.put("access_token", token)
        EventBus.getDefault().postSticky(LogEvent(true))
    }

    private fun makeAuthorizeUrl(): String {
        val r = Random(345345)
        val stateRandom = "${r.nextULong()}${r.nextULong()}"
        return "${AUTH_URL}?client_id=$GITHUB_ID&scope=$scope&redirect_uri=$REDIRECT_URI&state=$stateRandom"
    }

    fun logout() {
        this.token = ""
        Sp.put("access_token", "")
        EventBus.getDefault().postSticky(LogEvent(false))
    }

}