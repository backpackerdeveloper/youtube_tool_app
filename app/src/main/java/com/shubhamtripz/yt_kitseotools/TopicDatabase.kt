package com.shubhamtripz.yt_kitseotools

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Topic::class], version = 1, exportSchema = false)
abstract class TopicDatabase : RoomDatabase() {

    abstract fun topicDao(): TopicDao

    companion object {
        @Volatile
        private var INSTANCE: TopicDatabase? = null

        fun getDatabase(context: Context): TopicDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TopicDatabase::class.java,
                    "topic-db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
