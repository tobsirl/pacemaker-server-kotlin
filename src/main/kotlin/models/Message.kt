package models

import java.util.*

data class Message(
        var message: String = "",
        var id: String = UUID.randomUUID().toString()
        )