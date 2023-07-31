package com.shubhamtripz.yt_kitseotools

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class TagsActivity : AppCompatActivity() {

    private lateinit var videoUrlEditText: EditText
    private lateinit var getTagsButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        videoUrlEditText = findViewById(R.id.editText)
        getTagsButton = findViewById(R.id.button)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progrssBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TagAdapter(this)
        recyclerView.adapter = adapter

        getTagsButton.setOnClickListener {
            val videoUrl = videoUrlEditText.text.toString()
            GetTagsTask(adapter).execute(videoUrl)
        }
    }

    private inner class GetTagsTask(val adapter: TagAdapter) : AsyncTask<String, Void, List<String>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): List<String> {
            val url = params[0]
            val doc = Jsoup.connect(url).get()
            val metaTags: Elements = doc.select("meta[name=keywords]")
            val tagContent = metaTags.attr("content")
            return tagContent.split(",").map { it.trim() }
        }

        override fun onPostExecute(result: List<String>?) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE
            if (result != null) {
                adapter.setData(result)
            }
        }
    }
}