package com.example.todo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mEtUsername: EditText
    private lateinit var mEtPassword:EditText
    private lateinit var mBtnLogin:Button
    private lateinit var mBtnRegister:Button
    private lateinit var longinPreference:SharedPreferences
    private lateinit var mCbRemember:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

   private fun initView() {
       mEtUsername=findViewById(R.id.main_et_username)
       mEtPassword=findViewById(R.id.main_et_password)
       mBtnLogin=findViewById(R.id.main_btn_login)
       mBtnRegister=findViewById(R.id.main_btn_register)
       mCbRemember=findViewById(R.id.main_cb_remember)
       longinPreference=getSharedPreferences("remember",Context.MODE_PRIVATE)
       if(longinPreference.getBoolean("remember_password",false)){
           val username = longinPreference.getString("username","")
           val password = longinPreference.getString("password","")
           mEtUsername.setText(username)
           mEtPassword.setText(password)
           mCbRemember.isChecked=true
       }
       mBtnLogin.setOnClickListener{
           login()
       }
       mBtnRegister.setOnClickListener{
           register()
       }
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val codeSp=getSharedPreferences("code",Context.MODE_PRIVATE)
        val tureUsername=codeSp.getString("username","")
        val turePassword=codeSp.getString("password","")
        val username=mEtUsername.text.toString()
        val password=mEtPassword.text.toString()
        if(username == tureUsername && password == turePassword){
            loginSuccess()
        }else{
            longinFailure()
        }
    }

    private fun longinFailure() = Toast.makeText(this, "账号或者密码好像输错了 :(", Toast.LENGTH_SHORT).show()


    private fun loginSuccess() {
        val editor = longinPreference.edit()
        if (mCbRemember.isChecked) {
            editor.putString("username", mEtUsername.getText().toString())
            editor.putString("password", mEtPassword.getText().toString())
            editor.putBoolean("remember_password", mCbRemember.isChecked)
        } else {
            editor.clear()
        }
        editor.apply()
        Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TodoListActivity::class.java)
        startActivity(intent)
    }


}