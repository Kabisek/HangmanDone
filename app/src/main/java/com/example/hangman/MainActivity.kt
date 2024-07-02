package com.example.hangman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val getCreateGroupbutton = findViewById<Button>(R.id.playButton)
        getCreateGroupbutton.setOnClickListener{
            val Intent = Intent(this,Game::class.java)
            startActivity(Intent)
        }
    }
}

