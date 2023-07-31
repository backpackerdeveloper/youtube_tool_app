package com.shubhamtripz.yt_kitseotools

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopicPlannerActivity : AppCompatActivity() {

    private lateinit var addButton: ImageView
    private lateinit var cardContainer: LinearLayout
    private lateinit var topicDao: TopicDao
    private lateinit var topicDatabase: TopicDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_planner)

        addButton = findViewById(R.id.addButton)
        cardContainer = findViewById(R.id.cardContainer)

        topicDatabase = Room.databaseBuilder(applicationContext, TopicDatabase::class.java, "topic-db").build()
        topicDao = topicDatabase.topicDao()

        addButton.setOnClickListener {
            showAddTopicDialog()
        }

        // Load saved topics
        GlobalScope.launch(Dispatchers.Main) {
            val topics = getSavedTopics()
            topics.forEach { topic ->
                addTopicCard(topic.name)
            }
        }
    }

    private fun showAddTopicDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_topic, null)
        val topicEditText = dialogView.findViewById<EditText>(R.id.topicEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Topic")

        val dialog = dialogBuilder.create()
        dialog.show()

        saveButton.setOnClickListener {
            val topicName = topicEditText.text.toString()
            addTopicCard(topicName)
            saveTopic(topicName)
            dialog.dismiss()
        }
    }

    private fun addTopicCard(topicName: String) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.topic_card, null)
        val topicTextView = cardView.findViewById<TextView>(R.id.topicTextView)
        val deleteButton = cardView.findViewById<ImageView>(R.id.deleteButton)

        topicTextView.text = topicName

        deleteButton.setOnClickListener {
            cardContainer.removeView(cardView)
            removeTopic(topicName)
        }

        cardContainer.addView(cardView)
    }

    private fun saveTopic(topicName: String) {
        val topic = Topic(name = topicName)

        GlobalScope.launch(Dispatchers.IO) {
            topicDao.insert(topic)
        }
    }

    private fun removeTopic(topicName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            topicDao.delete(topicName)
        }
    }

    private suspend fun getSavedTopics(): List<Topic> {
        return topicDao.getAllTopics()
    }
}
