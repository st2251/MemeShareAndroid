package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme()
    {
        val pp = findViewById<ProgressBar>(R.id.progressBar)
        pp.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val meme = findViewById<ImageView>(R.id.memeImageView)

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).into(meme)
                pp.visibility=View.GONE
            },
            {
                Toast.makeText(this,"something went wrong:fix it",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun shareMeme(view: android.view.View) {
        val intt = Intent(Intent.ACTION_SEND)
        intt.type="text/plain"
        intt.putExtra(Intent.EXTRA_TEXT, "Hey..! CheckOut this cool meme I got...!! $currentImageUrl")
        val chooser = Intent.createChooser(intt,"Share this meme using..")
        startActivity(chooser)
    }
    fun nextMeme(view: android.view.View) {
        loadMeme()
    }
}


