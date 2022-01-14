package com.stefan.lab4.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Student(
    var userId: String = "",
    var index: String = "",
    var name: String = "",
    var surname: String = "",
    var phone: String = "",
    var address: String = "",
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "index" to index,
            "name" to name,
            "surname" to surname,
            "phone" to phone,
            "address" to address
        )
    }
}