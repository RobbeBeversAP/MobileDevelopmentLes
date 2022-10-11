package edu.ap.bevers.les2_intents

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import edu.ap.bevers.les2_intents.model.User

open class MainActivity : AppCompatActivity() {

    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        findViewById<Button>(R.id.btn_clearprefs).setOnClickListener{
            val userStorage =  getSharedPreferences("USER_STORAGE", Context.MODE_PRIVATE)
            val edit = userStorage.edit()
            edit.clear()
            edit.apply()
        }

        findViewById<TextView>(R.id.button_learn).setOnClickListener{
            startActivity(Intent(this, MainActivity3::class.java))
        }


        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                if(data != null)
                {

                    user = User(data.extras?.getString(User.USERNAME)!!,
                        data.extras?.getString(User.GENDER)!!, data.extras?.getInt(User.SKILLPOINTS)!!)
                    updateUserInterface(user)

                    //Save user
                    saveUser()

                }

            }
        }


        loadUser()


        if(!this::user.isInitialized)
        {
            val i = Intent(this, MainActivity2::class.java)
            resultLauncher.launch(i)
        }




        }

    private fun updateUserInterface(user: User): Unit {
        //TODO Show the new user values in the UI

        findViewById<TextView>(R.id.userlabel).text = user.userName
    }

    private fun loadUser()
    {
        val userStorage =  getSharedPreferences("USER_STORAGE", Context.MODE_PRIVATE)
        if(userStorage.getString(User.USERNAME,null) == null) return

        this.user = User(userStorage.getString(User.USERNAME, null)!!,
            userStorage.getString(User.GENDER, null)!!,
            userStorage.getInt(User.SKILLPOINTS, 0)
        )
        updateUserInterface(user)
    }

    private fun saveUser()
    {
        val userStorage =  getSharedPreferences("USER_STORAGE", Context.MODE_PRIVATE)
        val edit = userStorage.edit()
        edit.putString(User.USERNAME, user.userName)
        edit.putString(User.GENDER, user.gender)
        edit.putInt(User.SKILLPOINTS, user.skillPoints)
        edit.apply()
    }
}

