package com.github.higithub.ui.base

/**
 * Description:
 * Created By willke on 2022/7/17 10:45 上午
 */
interface IBaseView {

    fun showNoDataView()

    fun hideNoDataView()


    fun showLoadingView()

    fun hideLoadingView()


    fun showNetWorkErrView()

    fun hideNetWorkErrView()
}