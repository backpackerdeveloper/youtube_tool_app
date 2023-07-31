package com.shubhamtripz.yt_kitseotools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class DescriptionActivity : AppCompatActivity() {

    private lateinit var videoLink: EditText
    private lateinit var getButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        videoLink = findViewById(R.id.video_link)
        getButton = findViewById(R.id.get_button)

        getButton.setOnClickListener {
            val link = videoLink.text.toString().trim()
            if (link.isEmpty()) {
                videoLink.error = "Please enter a YouTube video link"
                videoLink.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(this, DescriptionShowActivity::class.java)
            intent.putExtra("link", link)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}