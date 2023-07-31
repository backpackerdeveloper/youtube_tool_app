package com.shubhamtripz.yt_kitseotools

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic")
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String
)
