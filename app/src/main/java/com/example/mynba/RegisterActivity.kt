package com.example.mynba

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynba.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var name = ""
    private var email = ""
    private var password = ""
    private var cPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.registerBtn.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {

        name = binding.usernameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        cPassword = binding.cPassword.text.toString().trim()

        if (name.isEmpty()) {
            toastMaker("Enter Username")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toastMaker("Invalid Email")
        } else if (cPassword.isEmpty()) {
            toastMaker("Confirm Password")
        } else if (password != cPassword) {
            toastMaker("Password Doesn't Match")
        } else {
            createUserAccount()
        }

    }

    private fun createUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                toastMaker("Failed to create Account function")
            }
    }

    private fun updateUserInfo() {
        val timestamp = System.currentTimeMillis()
        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["teamID"] = ""
        hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                toastMaker("Failed Saving User Info ${e.message} ")
            }

    }


    private fun toastMaker(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}