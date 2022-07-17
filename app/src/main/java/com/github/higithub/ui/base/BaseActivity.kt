package com.github.higithub.ui.base

import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity(), IBaseView {

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