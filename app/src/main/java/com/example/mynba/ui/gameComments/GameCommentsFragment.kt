package com.example.mynba.ui.gameComments


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.database.Comment
import com.example.mynba.databinding.CommentItemBinding
import com.example.mynba.databinding.FragmentGameCommentsBinding
import com.example.mynba.hideKeyboard
import com.example.mynba.snackBarMaker
import com.example.mynba.ui.games.KEY_GAME_ID
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "GameCommentsFragment"

class GameCommentsFragment : Fragment() {

    private lateinit var binding: FragmentGameCommentsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var commentText: String
    private lateinit var commentId: String
    private lateinit var uid: String
    private lateinit var gameId: String
    private var commentArray: ArrayList<Comment> = ArrayList()
    private val commentCollectionRef = Firebase.firestore.collection("comments")
    private val userCollectionRef = Firebase.firestore.collection("users")
    private val imageRef = Firebase.storage.reference
    private lateinit var image: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameId = requireArguments().getInt(KEY_GAME_ID).toString()
        firebaseAuth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameCommentsBinding.inflate(layoutInflater)
        binding.commentsRc.layoutManager = LinearLayoutManager(context)

        binding.sendComment.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                uid = firebaseAuth.currentUser!!.uid
                commentText = binding.commentET.text.toString()
                commentId = UUID.randomUUID().toString()
                val comment = Comment(uid, commentText, gameId, commentId)
                saveComment(comment)
                binding.commentET.text.clear()
            } else {
                Snackbar.make(it, getString(R.string.signInToComment), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.signIn)) {
                        findNavController().navigate(R.id.signInFragment)
                    }.show()
            }
            hideKeyboard(requireContext(), requireView())
        }

        commentsRealTime()

        return binding.root

    }

    private inner class CommentHolder(val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {

            binding.commentText.text = comment.comment
        }
    }

    private inner class CommentAdapter(commentArray: ArrayList<Comment>) :
        RecyclerView.Adapter<CommentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
            val binding: CommentItemBinding = CommentItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return CommentHolder(binding)
        }

        override fun onBindViewHolder(holder: CommentHolder, position: Int) {
            val comment = commentArray[position]
            holder.bind(comment)
            userName(comment.uid, holder)
            getImage(comment.uid, holder)
            holder.itemView.setOnClickListener {
                if (firebaseAuth.currentUser?.uid == comment.uid) {
                    deleteDialog(comment)
                }
            }
        }

        override fun getItemCount(): Int = commentArray.size

    }

    private fun deleteDialog(comment: Comment) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.deleteCommentMessage))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteComment(comment)
                snackBarMaker(requireView(), R.string.commentDeleted)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()

    }

    private fun deleteComment(comment: Comment) = CoroutineScope(Dispatchers.IO).launch {

        val commentQuery = commentCollectionRef
            .whereEqualTo("commentId", comment.commentId)
            .get()
            .await()
        if (commentQuery.documents.isNotEmpty()) {
            for (document in commentQuery) {
                try {
                    commentCollectionRef.document(document.id).delete().await()
                } catch (e: Exception) {
                    snackBarMaker(requireView(), R.string.commentFailed)
                }
            }
        }
    }

    private fun userName(uid: String, holder: GameCommentsFragment.CommentHolder) {
        userCollectionRef.document(uid)
            .get()
            .addOnCompleteListener {
                if (it.result.exists()) {
                    val username = it.result.getString("username")
                    holder.binding.commentUser.text = username
                }
            }
    }

    private fun commentsRealTime() {

        commentCollectionRef.whereEqualTo("gameId", gameId).addSnapshotListener { value, _ ->
            value?.let {
                commentArray.clear()
                for (document in it) {
                    val comment = document.toObject(Comment::class.java)
                    commentArray.add(comment)
                }
                binding.commentsRc.adapter = CommentAdapter(commentArray)
            }
        }
    }

    private fun getImage(userId: String, holder: GameCommentsFragment.CommentHolder) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                image = imageRef.child("images/myImage-${userId}").downloadUrl.await()
                holder.binding.commentIcon.load(image)
            } catch (e: java.lang.Exception) {
                Log.d(TAG, e.message.toString())
            }
        }

    private fun saveComment(comment: Comment) = CoroutineScope(Dispatchers.IO).launch {
        try {
            commentCollectionRef.document(commentId).set(comment).await()
        } catch (e: Exception) {
            snackBarMaker(requireView(), R.string.commentFailed)
        }
    }


}

