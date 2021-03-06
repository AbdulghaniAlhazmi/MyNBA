package com.example.mynba.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.mynba.R
import com.example.mynba.databinding.FragmentProfileBinding
import com.example.mynba.snackBarMaker
import com.example.mynba.toHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

const val REQUEST_CODE_IMAGE_PICK = 0
private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var curFile : Uri? = null
    private val imageRef = Firebase.storage.reference
    private val userCollectionRef = Firebase.firestore.collection("users")
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.signOut ->{
                firebaseAuth.signOut()
                toHome(requireContext())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        getImage()
        getUserInfo()

        binding.changePhotoBtn.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it,REQUEST_CODE_IMAGE_PICK)
            }
        }

        binding.updateButton.setOnClickListener {
            uploadImage("myImage-${firebaseAuth.currentUser?.uid}")
        }


        return binding.root
    }

    private fun getUserInfo(){

        val uid = firebaseAuth.currentUser?.uid

        if (uid != null) {
            userCollectionRef.document(uid)
                .get()
                .addOnCompleteListener {
                    if (it.result.exists()) {
                        val username = it.result.getString("username")
                        val email = it.result.getString("email")
                        val team = it.result.getString("favTeam")
                        binding.signupUsername.text = username
                        binding.signupEmail.text = email
                        binding.favoriteTeam.setText(team)

                    }
                }
        }
    }

   private fun getImage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val image = imageRef.child("images/myImage-${firebaseAuth.currentUser?.uid}").downloadUrl.await()
            binding.imageView.load(image)
        }catch (e:Exception){
            Log.d(TAG,e.localizedMessage)
        }
    }


    private fun uploadImage(filename : String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
                imageRef.child("images/$filename").putFile(it).await()
                snackBarMaker(requireView(),R.string.imageUploaded)
            }

        }catch (e:Exception){
            snackBarMaker(requireView(),R.string.userUpdateFail)        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK){
            data?.data?.let {
                curFile = it
                binding.imageView.setImageURI(it)
            }
        }
    }

}
