package com.example.mynba

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynba.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.skip.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

    }
}