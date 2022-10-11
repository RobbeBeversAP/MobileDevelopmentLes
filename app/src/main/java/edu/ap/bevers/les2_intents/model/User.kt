package edu.ap.bevers.les2_intents.model

data class User(var userName: String, var gender: String, var skillPoints: Int) {
    companion object{
        val USERNAME = "username"
        val GENDER = "gender"
        val SKILLPOINTS = "skillpoints"
    }
}