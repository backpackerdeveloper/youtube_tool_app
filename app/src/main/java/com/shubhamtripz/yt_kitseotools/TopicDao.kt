package com.shubhamtripz.yt_kitseotools

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.shubhamtripz.yt_kitseotools.Topic

@Dao
interface TopicDao {
    @Query("SELECT * FROM topic")
    suspend fun getAllTopics(): List<Topic>

    @Insert
    suspend fun insert(topic: Topic)

    @Delete
    suspend fun delete(topic: Topic)

    @Query("DELETE FROM topic WHERE name = :topicName")
    suspend fun delete(topicName: String)
}
