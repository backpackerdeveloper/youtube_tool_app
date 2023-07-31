package com.shubhamtripz.yt_kitseotools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class DescriptionShowActivity : AppCompatActivity() {

    private lateinit var titleCardView: CardView
    private lateinit var titleTextView: TextView
    private lateinit var copyTitleButton: ImageView
    private lateinit var descriptionCardView: CardView
    private lateinit var descriptionTextView: TextView
    private lateinit var copyDescriptionButton: ImageView
    private lateinit var progressBar: LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_show)

        titleCardView = findViewById(R.id.title_card)
        titleTextView = findViewById(R.id.title_text)
        copyTitleButton = findViewById(R.id.title_copy_button)

        descriptionCardView = findViewById(R.id.description_card)
        descriptionTextView = findViewById(R.id.description_text)
        copyDescriptionButton = findViewById(R.id.description_copy_button)

        progressBar = findViewById(R.id.progrssBar)

        val link = intent.getStringExtra("link")
        if (link != null) {
            getVideoInfo(link)
        } else {
            Toast.makeText(this, "Error retrieving video information", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getVideoInfo(link: String) {
        progressBar.visibility = View.VISIBLE

        val handler = Handler(Looper.getMainLooper())
        Thread {
            try {
                val doc: Document = Jsoup.connect(link).get()
                val title = doc.select("meta[property=og:title]").attr("content")
                val description = doc.select("meta[property=og:description]").attr("content")
                val thumbnailUrl = doc.select("meta[property=og:image]").attr("content")

                handler.post {
                    progressBar.visibility = View.GONE

                    titleTextView.text = title
                    descriptionTextView.text = description

                    Glide.with(this)
                        .load(thumbnailUrl)
                        .placeholder(R.drawable.picture)
                        .error(R.drawable.picture)
                        .into(findViewById(R.id.thumbnail_imageview))

                    setupCopyButtons(title, description)
                }
            } catch (e: IOException) {
                handler.post {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error retrieving video information", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }.start()
    }

    private fun setupCopyButtons(title: String, description: String) {
        copyTitleButton.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("title", title)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied title to clipboard", Toast.LENGTH_SHORT).show()
        }

        copyDescriptionButton.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("description", description)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied description to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
