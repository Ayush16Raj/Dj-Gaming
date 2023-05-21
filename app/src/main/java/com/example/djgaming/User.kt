package com.example.djgaming

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(val roomId: String? = null, val roomPassword: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}