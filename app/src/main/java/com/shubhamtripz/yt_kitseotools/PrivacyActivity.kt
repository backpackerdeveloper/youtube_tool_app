package com.shubhamtripz.yt_kitseotools

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.airbnb.lottie.LottieAnimationView

class PrivacyActivity : AppCompatActivity() {

    lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        mWebView = findViewById(R.id.privacyweb)
        mWebView.loadUrl("https://sites.google.com/view/ytkit/home")
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.settings.loadWithOverviewMode = true
        mWebView.settings.useWideViewPort = true
        mWebView.settings.builtInZoomControls = true
        mWebView.settings.displayZoomControls = false
        mWebView.settings.domStorageEnabled = true
        mWebView.webViewClient = object : WebViewClient() {

            val progrssBar = findViewById<LottieAnimationView>(R.id.progrssBar)

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progrssBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progrssBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

        }

    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            super.onBackPressed()

        }

    }

}