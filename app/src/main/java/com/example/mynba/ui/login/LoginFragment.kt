package com.example.mynba.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynba.R
import com.example.mynba.databinding.FragmentLoginBinding
import com.example.mynba.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            email = binding.loginEmail.text.toString().trim()
            password = binding.loginPassword.text.toString().trim()
            when {
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    toastMaker("Invalid Email")
                }
                password.isEmpty() -> {
                    toastMaker("Enter Password")
                }
                else -> {
                    signIn(email,password)
                }
            }
        }

        binding.signupRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

        return binding.root
    }

    private fun signIn(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()){task ->
                if (task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_news)
                }
                else{
                    toastMaker("Authentication Failed")
                }
            }
    }


    private fun toastMaker(message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }


}