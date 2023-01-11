package com.binar.kos.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.binar.kos.data.local.entity.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getUserDetail(): Flow<Users>
}