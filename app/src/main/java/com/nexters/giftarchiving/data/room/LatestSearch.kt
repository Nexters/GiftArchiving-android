package com.nexters.giftarchiving.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latestSearch")
data class LatestSearch(
    @PrimaryKey
    val keyword : String
)