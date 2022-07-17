package com.github.higithub.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val credentialProvider: () -> String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = credentialProvider()
        return if (token.isNotEmpty()) {
            val request: Request = chain.request()
            val authenticatedRequest: Request = request.newBuilder()
                .header("Authorization", "token $token").build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(chain.request())
        }
    }
}