package com.nexters.giftarchiving.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LatestSearch::class],version = 1)
abstract class LatestSearchDB : RoomDatabase(){
    abstract fun latestSearchDao() : LatestSearchDao

    companion object{
        private val DB_NAME = "latest-db"
        private var instance : LatestSearchDB? = null

        fun getInstance(context: Context): LatestSearchDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): LatestSearchDB {
            return Room.databaseBuilder(context.applicationContext, LatestSearchDB::class.java, DB_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                }).build()
        }
    }
}