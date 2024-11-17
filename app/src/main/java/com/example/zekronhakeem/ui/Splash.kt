package com.example.zekronhakeem.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.zekronhakeem.databinding.ActivitySplashBinding
import com.example.zekronhakeem.repo.local.db.LocalRepoImp
import com.example.zekronhakeem.ui.main.MainActivity
import kotlinx.coroutines.runBlocking

@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {
    private var length=12
    private lateinit var db: LocalRepoImp
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        db= LocalRepoImp(this)

        runBlocking {
              length=db.getSize()
          }
          Handler().postDelayed({
              if (length==114){
                 startActivity(Intent(this, MainActivity::class.java))
              }
            else{
                  startActivity(Intent(this, LoadingActivity::class.java))
              }
            finish()

        },1000)



        setContentView(binding.root)

    }



    }