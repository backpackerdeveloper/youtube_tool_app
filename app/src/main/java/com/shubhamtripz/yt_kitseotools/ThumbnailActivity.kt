package com.shubhamtripz.yt_kitseotools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.airbnb.lottie.LottieAnimationView

class ThumbnailActivity : AppCompatActivity() {

    private lateinit var videoLink: EditText
    private lateinit var getButton: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thumbnail)

        videoLink = findViewById(R.id.urlet)
        getButton = findViewById(R.id.loadbtn)

        getButton.setOnClickListener {
            val link = videoLink.text.toString().trim()
            if (link.isEmpty()) {
                videoLink.error = "Please enter a YouTube video link"
                videoLink.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(this, ThumbnailShowActivity::class.java)
            intent.putExtra("link", link)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }
}