package com.example.todo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity : AppCompatActivity() {
    private lateinit var mBtnRegister:Button
    private lateinit var mEtPassword: EditText
    private lateinit var mEtUsername: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    //初始相应的UI
    private fun initView() {
        mBtnRegister = findViewById(R.id.register_btn_register)
        mEtUsername = findViewById(R.id.register_et_username)
        mEtPassword = findViewById(R.id.register_et_password)
        mBtnRegister.setOnClickListener{
            val editor = getSharedPreferences("code", Context.MODE_PRIVATE).edit()
            editor.putString("username", mEtUsername.text.toString())
            editor.putString("password", mEtPassword.text.toString())
            editor.apply()
            finish()
        }
    }
}

