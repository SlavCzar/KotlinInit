package com.example.myapplication.activities

import android.net.Uri
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_news_web.*

class NewsWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web)


        web_view.webViewClient = (object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true;
            }
        })
        web_view.settings.javaScriptEnabled = true
        web_view.settings.cacheMode = WebSettings.LOAD_NO_CACHE;

        val url = intent.getStringExtra("url")
//        web_view.loadUrl(url)
        val builder = CustomTabsIntent.Builder()
        val tabIntent = builder.build()
        tabIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}