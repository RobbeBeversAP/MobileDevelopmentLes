package edu.ap.bevers.les2_intents

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import edu.ap.bevers.les2_intents.model.User

class MainActivity2 : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var skillPoints: EditText
    private lateinit var isFemaleCheckBox: CheckBox





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        userName = findViewById(R.id.userName)
        skillPoints = findViewById(R.id.et_points)

        isFemaleCheckBox = findViewById(R.id.female)
        isFemaleCheckBox.alpha = 0.4f
    }

    fun onClick(v: View)
    {

        //TODO This method is invoked when the add button is pressed. Show in a toast that
        // the user with name 'name' is added and finish the activity.

        Toast.makeText(this, "User with name ${userName.text} is added", Toast.LENGTH_SHORT).show()



        finish()
    }

    override fun finish(): Unit {

        val username = userName.text
        var gender = "Male"
        if(isFemaleCheckBox.isChecked) gender = "Female"
        val data = Intent()
        data.putExtra(User.USERNAME, username.toString())
        data.putExtra(User.GENDER, gender)
        data.putExtra(User.SKILLPOINTS, skillPoints.text.toString().toInt())
        setResult(RESULT_OK, data)
        super.finish()


    }
}