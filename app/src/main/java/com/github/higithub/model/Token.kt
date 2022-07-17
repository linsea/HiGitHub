package com.github.higithub.model

import androidx.annotation.Keep

/**
 * Description:
 * Created By willke on 2022/7/16 6:05 下午
 */
@Keep
data class Token(
    val access_token: String,
    val token_type: String,
    val scope: String
)
