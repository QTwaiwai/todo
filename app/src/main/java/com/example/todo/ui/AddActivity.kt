package com.example.todo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddActivity : AppCompatActivity() {
    private lateinit var mEtContext: EditText
    private lateinit var mBtnBuild: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initView()
    }

    private fun initView() {
        mEtContext=findViewById(R.id.et_add_context)
        mBtnBuild=findViewById(R.id.btn_add_build)
        mBtnBuild.setOnClickListener{
            val intent = Intent(this, TodoListActivity::class.java).apply {
                putExtra("笔记内容", mEtContext.text.toString())
                putExtra("笔记时间", dateToString())
            }
            setResult(RESULT_FIRST_USER, intent)
            finish()
        }
    }

    private fun dateToString(): String {
            val date=Date()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            return simpleDateFormat.format(date)
    }
}