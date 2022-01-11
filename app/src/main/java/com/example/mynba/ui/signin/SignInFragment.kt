package com.example.mynba.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynba.MainActivity
import com.example.mynba.R
import com.example.mynba.databinding.FragmentSigninBinding
import com.example.mynba.firebaseAuth
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


private const val TAG = "LoginFragment"

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        Log.d(TAG, firebaseAuth.currentUser?.uid.toString())

        binding.signInButton.setOnClickListener {
            loginUser()
        }


        binding.signupRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signup)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        checkLoggedIn()
    }

    private fun loginUser() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email,password).await()
                    checkLoggedIn()
                }catch (e: Exception){
                    Snackbar.make(requireView(), e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkLoggedIn() {
        if (firebaseAuth.currentUser == null){

        }else{
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            Snackbar.make(requireView(), getString(R.string.logged), Snackbar.LENGTH_LONG).show()

        }
    }
}