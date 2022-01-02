package com.example.mynba.ui.signup

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynba.R
import com.example.mynba.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var username = ""
    private var email = ""
    private var password = ""
    private var cPassword = ""
    private var teamId = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            validateData()
        }

        binding.loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }


        return binding.root
    }

    private fun validateData() {
        username = binding.signupUsername.text.toString().trim()
        email = binding.signupEmail.text.toString().trim()
        password = binding.signupPassword.text.toString().trim()
        cPassword = binding.signupCpassword.text.toString().trim()

        when {
            username.isEmpty() -> {
                toastMaker("Enter User Name")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                toastMaker("Invalid Email")
            }
            cPassword != password -> {
                toastMaker("Password Doesn't Match")
            }
            else -> {
                createUser()
            }
        }

    }

    private fun createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                toastMaker("Failed To Create Account ${it.message}")
            }
    }

    private fun updateUserInfo() {
        val uid = firebaseAuth.uid
        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
            "email" to email,
            "teamId" to teamId
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d(TAG,"Added with id : ${it.id}")
            }
            .addOnFailureListener {
                Log.d(TAG,"error ${it.message}")
            }
    }

    private fun toastMaker(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }


}
