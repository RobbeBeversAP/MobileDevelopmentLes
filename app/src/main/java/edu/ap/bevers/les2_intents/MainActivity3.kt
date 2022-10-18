package edu.ap.bevers.les2_intents

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import edu.ap.bevers.les2_intents.model.Quote
import edu.ap.bevers.les2_intents.model.User
import edu.ap.bevers.les2_intents.persistance.DatabaseHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.net.URL


private val json = Json { ignoreUnknownKeys = true }

class MainActivity3 : AppCompatActivity() {

    private var skillpoints: Int = 0

    private var db: DatabaseHelper = DatabaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        skillpoints = loadPoints()
        findViewById<TextView>(R.id.tv_points).text = skillpoints.toString()


        findViewById<Button>(R.id.btn_generate).setOnClickListener {
            val url = URL("https://stoic-server.herokuapp.com/random")

            Thread {
                val result = getURLContentsAsString(url)
                runOnUiThread {

                    val quote = json.decodeFromString<List<Quote>>(result)
                    showQuote(quote[0].body)
                    db.addQuote(quote[0].body, quote[0].author, quote[0].quotesource)
                    Toast.makeText(this, quote[0].author, Toast.LENGTH_SHORT).show()
                    addPointsAndSave(10)
                }
            }.start()
        }
    }

    private fun loadPoints() : Int
    {
        val userStorage =  getSharedPreferences("USER_STORAGE", Context.MODE_PRIVATE)
        return userStorage.getInt(User.SKILLPOINTS, 0)
    }

    private fun addPointsAndSave(points: Int)
    {
        skillpoints += points
        val userStorage =  getSharedPreferences("USER_STORAGE", Context.MODE_PRIVATE)
        val edit = userStorage.edit()
        edit.putInt(User.SKILLPOINTS, skillpoints)
        edit.apply()
        findViewById<TextView>(R.id.tv_points).text = skillpoints.toString()
    }

    private fun showQuote(quote: String)
    {
        findViewById<TextView>(R.id.tv_quote).text = quote
    }

    private fun getURLContentsAsString(ourUrl: URL): String {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(ourUrl)
            .build()

        val call = client.newCall(request)
        val response = call.execute()

        return response.body!!.string()
    }
}