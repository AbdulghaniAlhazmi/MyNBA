package com.example.mynba.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynba.R
import com.example.mynba.database.User
import com.example.mynba.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var binding : FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("users")
    private lateinit var username : String
    private lateinit var uid : String
    private lateinit var password : String
    private lateinit var email : String
    private var favTeam : String = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            username = binding.signupUsername.text.toString()
            email = binding.signupEmail.text.toString()
            password = binding.signupPassword.text.toString()
            registerUser()

        }

        binding.loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }


        return binding.root
    }

    private fun registerUser() {

        if (email.isNotEmpty() && password.isNotEmpty())
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                    uid = firebaseAuth.currentUser?.uid.toString()
                    val user = User(uid,email,favTeam,username)
                    updateUser(user)
                    findNavController().navigate(R.id.action_signUpFragment_to_navigation_standings)
                }catch (e : Exception){
                    withContext(Dispatchers.Main){
                        Snackbar.make(requireView(), "Failed To Create User", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

    }

    private fun updateUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        val uid = firebaseAuth.currentUser?.uid

        try {
            userCollectionRef.document(uid.toString()).set(user).await()
            Snackbar.make(requireView(), "User Registered Success", Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            Snackbar.make(requireView(), "Failed to Register User info", Snackbar.LENGTH_LONG).show()

        }
    }

    override fun onResume() {
        super.onResume()
        val teams = resources.getStringArray(R.array.teams)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,teams)

        with(binding.favoriteTeam){
            setAdapter(arrayAdapter)
            onItemClickListener = this@SignUpFragment
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            favTeam = parent.getItemAtPosition(position).toString()
        }
    }



}
