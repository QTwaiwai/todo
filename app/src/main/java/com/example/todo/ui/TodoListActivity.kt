package com.example.todo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.CRUD
import com.example.todo.R
import com.example.todo.adapter.NoteRvAdapter
import com.example.todo.bean.NoteData
import com.example.todo.databinding.ActivityTodolistBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListActivity : AppCompatActivity() {

    private lateinit var mBtnAdd:FloatingActionButton
    private var _noteList: MutableList<NoteData> = mutableListOf()

    private val noteList: MutableList<NoteData>
        get() = _noteList

    private val mBinding: ActivityTodolistBinding by lazy {
        ActivityTodolistBinding.inflate(layoutInflater)
    }

    private lateinit var mAdapter: NoteRvAdapter



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initData()
        initRv()
        initClick()
    }

    private fun initData() {
        val op= CRUD(applicationContext)
        op.open()
        if(_noteList.size>0){
            _noteList.clear()
        }
        _noteList.addAll(op.getAllNotes())
        op.close()
    }

    private fun initClick() {
        mBtnAdd=findViewById(R.id.btn_todolist_add)
        mBtnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            launchForResult.launch(intent)
        }
    }



    private val launchForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                val data= result.data
                // 从data Intent中提取数据
                val content = data?.getStringExtra("笔记内容")
                val date = data?.getStringExtra("笔记时间")
                // 处理结果数据
                val note = NoteData(content!!,date!!,1)
                val op= CRUD(applicationContext)
                op.open()
                when(result.resultCode){
                    Activity.RESULT_FIRST_USER->{
                        op.addNote(note)
                    }
                    Activity.RESULT_OK -> {
                        op.updateNote(note)
                    }
                    Activity.RESULT_CANCELED -> {
                        op.deleteNote(note)
                    }
                }
                op.close()
                refreshListView()
            }


    private fun initRv(){
        mAdapter = NoteRvAdapter(this,noteList)
        mBinding.rvTodolist.apply {
            layoutManager=LinearLayoutManager(this@TodoListActivity)
            adapter=mAdapter.apply {
                setOnItemClickListener {
                    val intent = Intent(this@TodoListActivity, ChangeActivity::class.java).apply {
                        putExtra("笔记内容", _noteList[it].content)
                        putExtra("笔记时间", _noteList[it].time)
                    }
                    launchForResult.launch(intent)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshListView(){
        val op= CRUD(applicationContext)
        op.open()
        if(_noteList.size>0){
                _noteList.clear()
        }
        _noteList.addAll(op.getAllNotes())
        op.close()
        mAdapter.notifyDataSetChanged()
    }
}