package com.nexters.giftarchiving.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LatestSearchDao {
    @Query("SELECT * FROM latestSearch")
    fun getAll() : LiveData<List<LatestSearch>>

    @Query("DELETE FROM latestSearch WHERE keyword=:keyword")
    fun deleteSearch(keyword: String)

    @Query("INSERT INTO latestSearch VALUES (:keyword)")
    fun insert(keyword: String)
}