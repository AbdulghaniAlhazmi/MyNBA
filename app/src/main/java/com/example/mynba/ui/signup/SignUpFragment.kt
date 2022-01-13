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
import com.example.mynba.hideKeyboard
import com.example.mynba.toHome
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpFragment : Fragment(), AdapterView.OnItemClickListener {



    private lateinit var binding : FragmentSignUpBinding
    private lateinit var username : String
    private lateinit var password : String
    private lateinit var email : String
    private lateinit var uid : String
    private var favTeam : String = ""
    private lateinit var firebaseAuth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("users")


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
            hideKeyboard(requireContext(),requireView())
        }

        binding.signInRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_signIn)
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
                }catch (e : Exception){
                    snackBarMaker(R.string.filedRegister)
                }
            }
    }

    private fun updateUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        val uid = firebaseAuth.currentUser?.uid
        try {
            userCollectionRef.document(uid.toString()).set(user).await()
            toHome(requireContext())
        } catch (e: Exception) {
           snackBarMaker(R.string.filedRegister)

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


    private fun snackBarMaker(message: Int){

        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}
