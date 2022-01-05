package com.example.mynba

class ModelComment {

    var uid = ""
    var comment = ""
    var commentId = ""
    var gameId = ""

    constructor()


    constructor(uid: String, comment: String, commentId: String, gameId: String) {
        this.uid = uid
        this.comment = comment
        this.commentId = commentId
        this.gameId = gameId
    }


}