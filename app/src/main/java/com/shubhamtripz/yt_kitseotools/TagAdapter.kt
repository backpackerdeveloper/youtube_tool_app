package com.shubhamtripz.yt_kitseotools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagAdapter(private val context: Context) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    private var tagList: List<String> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagTextView: TextView = itemView.findViewById(R.id.tagTextView)
        val copyButton: Button = itemView.findViewById(R.id.copyButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tagList[position]
        holder.tagTextView.text = tag
        holder.copyButton.setOnClickListener {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Tag", tag)
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun setData(tags: List<String>) {
        tagList = tags
        notifyDataSetChanged()
    }
}
