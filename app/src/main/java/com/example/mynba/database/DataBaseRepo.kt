package com.example.mynba.database

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val TAG = "DataBaseRepo"

class DataBaseRepo {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userCollectionRef = Firebase.firestore.collection("users")


    suspend fun createUser(user: User, password : String) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
                user.uid = firebaseAuth.currentUser?.uid ?: ""
                userCollectionRef.document(user.uid).set(user)
            }catch (e: Exception){
                Log.d(TAG,e.localizedMessage)
            }
    }


    suspend fun signInUser(email : String , password: String){
        try {
            firebaseAuth.signInWithEmailAndPassword(email,password)
        }catch (e : Exception){
            Log.d(TAG,e.localizedMessage)
        }
    }



}