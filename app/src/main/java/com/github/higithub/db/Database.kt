package com.github.higithub.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Description:
 * Created By willke on 2022/7/17 2:53 下午
 */

@Entity
data class UserProfile constructor(val json: String, @PrimaryKey val id: Int = 0)

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @get:Query("select * from UserProfile where id = 0")
    val userProfileLiveData: LiveData<UserProfile?>
}


@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
abstract class GitHubDatabase : RoomDatabase() {
    abstract val userProfileDao: UserProfileDao
}

private lateinit var INSTANCE: GitHubDatabase

fun getDatabase(context: Context): GitHubDatabase {
    synchronized(GitHubDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    GitHubDatabase::class.java,
                    "github_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
