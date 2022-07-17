package com.github.higithub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.greenrobot.eventbus.EventBus

/**
 * Description:
 * Created By willke on 2022/7/17 10:39 上午
 */
open class BaseViewModel: ViewModel() {

    private var registered = false

    fun registerEventBus() {
        if (!registered) {
            registered = true
            EventBus.getDefault().register(this)
        }
    }

    override fun onCleared() {
        if (registered) {
            registered = false
            EventBus.getDefault().unregister(this)
        }
        super.onCleared()
    }
}