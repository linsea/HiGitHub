package com.github.higithub.ui.base

import androidx.fragment.app.Fragment

/**
 * Description:
 * Created By willke on 2022/7/17 10:39 上午
 */
open class BaseFragment : Fragment(), IBaseView {

    override fun showNoDataView() {
    }

    override fun hideNoDataView() {
    }

    override fun showLoadingView() {
    }

    override fun hideLoadingView() {
    }

    override fun showNetWorkErrView() {
    }

    override fun hideNetWorkErrView() {
    }
}