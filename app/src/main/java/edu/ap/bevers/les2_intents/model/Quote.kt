package edu.ap.bevers.les2_intents.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(val id: Int, val body: String, val author: String, val quotesource: String) {
}