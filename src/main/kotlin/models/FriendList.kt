package models

data class FriendList(

        val email: String = "", //From what I've read you can't extend a class in Kotlin
        var messages: String
)