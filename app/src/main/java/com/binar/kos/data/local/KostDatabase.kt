package com.binar.kos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.kos.data.local.dao.UserDao
import com.binar.kos.data.local.entity.Users

@Database(
    entities = [Users::class],
    version = 1
)
abstract class KostDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: KostDatabase? = null

        fun getInstance(context: Context): KostDatabase? {
            if (INSTANCE == null) {
                synchronized(KostDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        KostDatabase::class.java, "Kost.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}
