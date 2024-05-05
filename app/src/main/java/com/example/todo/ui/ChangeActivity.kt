package com.example.todo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R

class ChangeActivity : AppCompatActivity() {
    private lateinit var mEtContext: EditText
    private lateinit var mBtnBuild: Button
    private lateinit var mBtnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)
        initView()
    }

    private fun initView() {
        mEtContext=findViewById(R.id.et_change_context)
        val content = intent.getStringExtra("笔记内容")
        val time = intent.getStringExtra("笔记时间")
        mEtContext.setText(content)
        mBtnBuild=findViewById(R.id.btn_change_build)
        mBtnBuild.setOnClickListener{
            val intent = Intent(this, TodoListActivity::class.java).apply {
                putExtra("笔记内容", mEtContext.text.toString())
                putExtra("笔记时间", time)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        mBtnCancel=findViewById(R.id.btn_change_cancel)
        mBtnCancel.setOnClickListener{
            val intent = Intent(this, TodoListActivity::class.java).apply {
                putExtra("笔记内容", mEtContext.text.toString())
                putExtra("笔记时间", time)
            }
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}