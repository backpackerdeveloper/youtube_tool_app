package com.shubhamtripz.yt_kitseotools

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : AppCompatActivity() {

    private  var appUpdate: AppUpdateManager? = null
    private val REQUEST_CODE = 100
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Playstore inApp Update code
        appUpdate = AppUpdateManagerFactory.create(this)
        checkUpdate()
        // Platsore in app update ends here for on create method


        // Internet connection check code
        if (!isInternetAvailable()) {
            internetpopup()
        } else {
            // Perform your normal functionality here
        }

        // Internet connection check code ends here


        val trendbtn = findViewById<RelativeLayout>(R.id.trendingbtn)
        trendbtn.setOnClickListener {

            val intent = Intent(this, TrendingPageActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }


        val tagbtn = findViewById<RelativeLayout>(R.id.tagsbtn)
        tagbtn.setOnClickListener {

            val intent = Intent(this, TagsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        val desbtn = findViewById<RelativeLayout>(R.id.descriptionbtn)
        desbtn.setOnClickListener {

            val intent = Intent(this, DescriptionActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        val thumbbtn = findViewById<RelativeLayout>(R.id.thumbnailbtn)
        thumbbtn.setOnClickListener {

            val intent = Intent(this, ThumbnailActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        val topicbtn = findViewById<RelativeLayout>(R.id.topicbtn)
        topicbtn.setOnClickListener {

            val intent = Intent(this, TopicPlannerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        val mbtn = findViewById<ImageView>(R.id.morebtn)
        mbtn.setOnClickListener {

            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

    }

    // Playstore Update Methods

    fun checkUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if (updateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                appUpdate?.startUpdateFlowForResult(updateInfo, AppUpdateType.IMMEDIATE, this, REQUEST_CODE)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if (updateInfo.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdate?.startUpdateFlowForResult(updateInfo, AppUpdateType.IMMEDIATE, this, REQUEST_CODE)
            }

        }
    }

    // Playstore methods end

    // Check Internet Connection Code

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            // For devices with API 23 and above
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                ) {
                    return true
                }
            }
        }
        return false
    }

    // Check Internet Connection Code end

    // Internet connection Popup

    private fun internetpopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.no_internet, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val okButton = dialogView.findViewById<Button>(R.id.okButton)

        okButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    // Internet connection Popup end
}