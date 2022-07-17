package com.github.higithub.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.higithub.db.UserProfileDao
import com.github.higithub.model.GithubUserModel
import com.github.higithub.network.GitHubApi
import com.google.gson.Gson
import kotlinx.coroutines.withTimeout

/**
 * Description:
 * Created By willke on 2022/7/17 3:05 下午
 */
class UserProfileRepository(
    private val service: GitHubApi,
    private val userProfileDao: UserProfileDao
) {

    val userProfile: LiveData<GithubUserModel?> = userProfileDao.userProfileLiveData.map {
        if (it?.json != null) {
            kotlin.runCatching {
                Gson().fromJson(it.json, GithubUserModel::class.java)
            }.getOrNull()
        } else {
            null
        }
    }

    suspend fun refreshUserProfile() {
        val result = withTimeout(5_000) {
            service.getUserInfo()
        }
        userProfileDao.insertUserProfile(UserProfile(Gson().toJson(result)))
    }

}
