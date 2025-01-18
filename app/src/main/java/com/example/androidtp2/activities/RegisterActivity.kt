package com.example.androidtp2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.androidtp2.R
import com.example.androidtp2.api.Api
import com.example.androidtp2.data.RegisterData

class RegisterActivity : AppCompatActivity() {

    public fun goToLogin(view: View)
    {
        finish();
    }

    private fun register()
    {
        val name = findViewById<EditText>(R.id.txtRegisterName).text.toString()
        val mail = findViewById<EditText>(R.id.txtRegisterMail).text.toString()
        val password = findViewById<EditText>(R.id.txtRegisterPassword).text.toString()
        val registerData = RegisterData(name, mail, password)
        Api().post("https://mypizza.lesmoulinsdudev.com/register", registerData, ::registerSuccess)
    }

    private fun registerSuccess(responseCode: Int) {
            if (responseCode == 200) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            register()
        }
    }
}