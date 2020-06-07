package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.squareup.picasso.Picasso


class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val intent = getIntent()
        val headline  = intent.getStringExtra("headline")
        val content  = intent.getStringExtra("content")
        val imageUrl  = intent.getStringExtra("imageUrl")

        val imageView = findViewById<ImageView>(R.id.detail_image)
        val headlineTxt = findViewById<TextView>(R.id.detail_headline)
        val contentTxt = findViewById<TextView>(R.id.detail_content)

        Picasso.with(applicationContext).load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(imageView)

        headlineTxt.setText(headline)
        contentTxt.setText(content)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}