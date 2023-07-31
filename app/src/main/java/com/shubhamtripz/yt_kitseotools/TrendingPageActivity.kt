package com.shubhamtripz.yt_kitseotools

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class TrendingPageActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var btnSelectCountry: Button
    var selectedCountry = "US" // default country is US

    @SuppressLint("SetJavaScriptEnabled")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_page)

        webView = findViewById(R.id.trendingwebview)
        btnSelectCountry = findViewById(R.id.btn_select_country)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // hide loading progress or animation here
            }
        }

        // load the default trending page of the current live country
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.youtube.com/feed/trending?gl=$selectedCountry")

        // show the country selection dialog when the button is clicked
        btnSelectCountry.setOnClickListener {
            val countryList = arrayOf(
                "US", "IN", "GB", "JP", "DZ",
                "AR", "BH", "BR", "FR", "DE",
                "GE", "HN", "HK", "IQ", "JP",
                "KZ", "KE", "KW", "LV", "LT",
                "MY", "MX", "MA", "NL", "NZ",
                "PK", "NO", "PL", "QA", "SA",
                "RS", "SG", "ZA", "TW", "TH",
                "TR", "UG", "AE", "VN", "ZW",
                "VE", "YE", "CH", "SE", "ES",
                "KR", "LK", "RU", "PH", "NP"
            )
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Select Country")
            dialogBuilder.setSingleChoiceItems(countryList, -1, DialogInterface.OnClickListener { dialog, which ->
                selectedCountry = countryList[which]
                dialog.dismiss()
                // load the trending page of the selected country
                webView.loadUrl("https://www.youtube.com/feed/trending?gl=$selectedCountry")
            })
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }
}