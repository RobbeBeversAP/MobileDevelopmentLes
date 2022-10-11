package edu.ap.bevers.les2_intents.model

import kotlinx.serialization.Serializable

@Serializable
class Quote(val id: Int, val body: String, val author: String) {
}