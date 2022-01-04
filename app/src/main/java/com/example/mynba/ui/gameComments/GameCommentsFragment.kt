package com.example.mynba.ui.gameComments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynba.R
import com.example.mynba.databinding.FragmentGameCommentsBinding
import com.example.mynba.ui.games.KEY_GAME_ID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class GameCommentsFragment : Fragment() {

    private lateinit var binding: FragmentGameCommentsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var comment: String
    private lateinit var gameId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameId = requireArguments().getInt(KEY_GAME_ID).toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameCommentsBinding.inflate(layoutInflater)
        binding.commentsRc.layoutManager = LinearLayoutManager(context)


        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSend.setOnClickListener {
            if (firebaseAuth.currentUser == null) {
                Toast.makeText(context, "Not Loged In", Toast.LENGTH_SHORT).show()
            } else {
                addComment()
            }
        }

        return binding.root

    }

    private fun addComment() {
        comment = binding.commentEt.text.toString()

        if (comment.isEmpty()){

        }else{
            sendComment()
        }
    }

    private fun sendComment() {
        val uid = firebaseAuth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val timestamp = System.currentTimeMillis()

        val comments = hashMapOf(
            "uid" to uid,
            "gameId" to gameId ,
            "comment" to comment,
            "timestamp" to timestamp
        )

        db.collection("comments")
            .add(comments)
            .addOnSuccessListener {
                Toast.makeText(context,"Comment Added",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()

            }
    }


}