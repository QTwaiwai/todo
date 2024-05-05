package com.example.todo.bean

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabase(val context: Context,name:String,version: Int): SQLiteOpenHelper(context,name,null,version) {

    private val createNote = "create table notes (" +
            "_id integer primary key autoincrement," +
            "content text," +
            "time text," +
            "tag text)"



    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createNote)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       //更新数据库
        db?.execSQL("drop table if exists notes")
        onCreate(db)
        if(oldVersion<2){
            db?.execSQL("alter table notes add column tag text")
        }


    }

    constructor(context: Context):this(context,"notes",1)

}