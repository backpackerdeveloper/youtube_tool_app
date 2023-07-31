package com.shubhamtripz.yt_kitseotools

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ThumbnailShowActivity : AppCompatActivity() {

    private lateinit var imageViewThumbnail: ImageView
    private lateinit var savebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thumbnail_show)

        imageViewThumbnail = findViewById(R.id.imageViewThumbnail)
        savebtn = findViewById(R.id.savebtn)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

        savebtn.setOnClickListener {

            val bitmap = getImageofView(imageViewThumbnail)
            if (bitmap!= null){
                saveToStorage(bitmap)
            }
        }


        val youtubeUrl = intent.getStringExtra("link")
        val videoId = youtubeUrl?.let { it1 -> extractVideoId(it1) }
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        Glide.with(this)
            .load(thumbnailUrl)
            .into(imageViewThumbnail)

    }

    private fun extractVideoId(url: String): String {
        val videoId: String
        if (url.contains("youtube.com/watch?v=")) {
            val split = url.split("v=")
            videoId = split[1]
        } else if (url.contains("youtu.be/")) {
            val split = url.split("be/")
            videoId = split[1]
        } else {
            videoId = ""
        }
        return videoId
    }

    private fun saveToStorage(bitmap: Bitmap) {

        val imageName = "thumbnail_${System.currentTimeMillis()}.jpg"
        var fos : OutputStream? = null
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            this.contentResolver?.also {resolver->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri : Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                // successfully saved method call
                savedpopup()
            }
        }

        else{

            val imageDirectory =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imageDirectory, imageName)
            fos = FileOutputStream(image)
        }
    }

    private fun getImageofView(view: ImageView): Bitmap? {

        var image : Bitmap? = null
        try {

            image = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(image  )
            view.draw(canvas)
        } catch (e: java.lang.Exception){
            Log.e("Shubhamtripz", "Cannot Save")
        }
        return image
    }

    // Popup message method code

    private fun savedpopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.save_image, null)

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

    // Popup message method code


}
