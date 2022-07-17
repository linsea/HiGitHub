package com.github.higithub.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.higithub.db.UserProfileRepository
import com.github.higithub.event.LogEvent
import com.github.higithub.login.AuthManager
import com.github.higithub.model.GithubUserModel
import com.github.higithub.network.ApiService
import com.github.higithub.ui.base.BaseViewModel
import com.github.higithub.utils.singleArgViewModelFactory
import kotlinx.coroutines.launch
import logcat.asLog
import logcat.logcat
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineViewModel(private val repository: UserProfileRepository) : BaseViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::MineViewModel)
    }

    private var _logEvent = MutableLiveData<LogEvent>()
    val logEvent: LiveData<LogEvent> = _logEvent

//    private var _userInfo = MutableLiveData<GithubUserModel>()
//    val userInfo = _userInfo

    val userInfo: LiveData<GithubUserModel?> = repository.userProfile


    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?>
        get() = _message


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onLogEvent(event: LogEvent) {
        logcat { "onLogEvent : ${event.isLogin}" }
        _logEvent.value = event
        if (event.isLogin) {
            getUserInfo()
        }
    }

    private fun getUserInfo() {
        logcat { "getUserInfo" }
        viewModelScope.launch {
            try {
                val userInfo = repository.refreshUserProfile()
                val b = 0;
            } catch (e: Exception) {
                logcat { "getUserInfo err: ${e.asLog()}" }
                _message.postValue(e.asLog())
            }
        }

    }


//    private fun getUserInfo() {
//        logcat { "getUserInfo" }
//        viewModelScope.launch {
//            try {
//                val userInfo = ApiService.getUserInfo()
//                _userInfo.value = userInfo
//            } catch (e: Exception) {
//                logcat { "getUserInfo err: ${e.asLog()}" }
//            }
//        }
//
//    }

    fun logout() {
        AuthManager.logout()
    }


}