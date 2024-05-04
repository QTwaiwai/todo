package com.example.todo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R


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
        mEtUsername = findViewById(R.id.et_register_username)
        mEtPassword = findViewById(R.id.et_register_password)
        mBtnRegister.setOnClickListener{
            val editor = getSharedPreferences("code", Context.MODE_PRIVATE).edit()
            editor.putString("username", mEtUsername.text.toString())
            editor.putString("password", mEtPassword.text.toString())
            editor.apply()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("用户名", mEtUsername.text.toString())
                putExtra("密码",mEtPassword.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

