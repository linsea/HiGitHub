package com.github.higithub.network

import com.github.higithub.model.GithubUserModel
import com.github.higithub.model.Token
import retrofit2.Response
import retrofit2.http.*

/**
 * Description:
 * Created By willke on 2022/7/16 4:51 下午
 */
interface GitHubApi {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST
    suspend fun getAccessToken(
        @Url url: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): Token


    @Headers("Content-Type: application/json")
    @GET("/user")
    suspend fun getUserInfo(): GithubUserModel


}