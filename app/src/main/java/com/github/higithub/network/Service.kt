package com.github.higithub.network

import com.github.higithub.login.AuthManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor(AuthManager.tokenProvider))
    .addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    })
    .build()

val ApiService: GitHubApi by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(GitHubApi::class.java)
}