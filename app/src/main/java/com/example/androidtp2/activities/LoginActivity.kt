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
import com.example.androidtp2.data.LoginData

class LoginActivity : AppCompatActivity() {

    public fun registerNewAccount(view: View)
    {
        val intent = Intent(this, RegisterActivity::class.java);
        startActivity(intent);
    }

    private fun login() {
        val mail = findViewById<EditText>(R.id.txtMail).text.toString()
        val password = findViewById<EditText>(R.id.txtPassword).text.toString()
        val data = LoginData(mail, password)

        Api().post("https://mypizza.lesmoulinsdudev.com/auth", data, ::loginSuccess)
    }

    private fun loginSuccess(responseCode: Int, token: String?) {
            if (responseCode == 200 && token != null) {
                val intent = Intent(this, OrdersActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.btnConect).setOnClickListener {
            login()
        }
    }
}