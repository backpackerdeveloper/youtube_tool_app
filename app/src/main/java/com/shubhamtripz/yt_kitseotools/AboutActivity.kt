package com.shubhamtripz.yt_kitseotools

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val privacybt = findViewById<CardView>(R.id.privacybtn)
        privacybt.setOnClickListener {

            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        val rate = findViewById<CardView>(R.id.rateusbtn)
        rate.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.shubhamtripz.yt_kitseotools"))
            startActivity(i)
        }

        val share_text_1_btn = findViewById<CardView>(R.id.sharebtn)

        share_text_1_btn.setOnClickListener { view ->
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Grow your YT channel with Seo Tools - Download now from playstore: https://play.google.com/store/apps/details?id=com.shubhamtripz.yt_kitseotools"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }
}