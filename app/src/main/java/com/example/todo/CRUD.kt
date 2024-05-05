package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todo.bean.NoteData
import com.example.todo.bean.NoteDatabase

class CRUD {
   private var dbHandler: SQLiteOpenHelper
   private lateinit var db:SQLiteDatabase
   private val columns= arrayOf("_id","content","time","tag")

   constructor(context: Context){
       dbHandler=NoteDatabase(context)
   }

    fun open(){
        db=dbHandler.writableDatabase
    }

    fun close(){
        dbHandler.close()
    }
    //增加笔记到数据库
    fun addNote(note:NoteData):NoteData{
        //add a note object to database
        note.id=db.insert("notes",null, ContentValues().apply {
            put("content",note.content)
            put("time",note.time)
            put("tag",note.tag)
        })
        return note
    }
    //更新笔记到数据库
    fun updateNote(note:NoteData){
        db.execSQL("update notes set content = ? where time=?", arrayOf(note.content,note.time))
    }
    //从数据库删除笔记
    fun deleteNote(note:NoteData){
        db.execSQL("delete from notes where time=?", arrayOf(note.time))
    }
    //获取数据库全部笔记
    fun getAllNotes():ArrayList<NoteData>{
        val notes=ArrayList<NoteData>()
        val cursor=db.query("notes",columns,null,null,null,null,null)
        if(cursor.count>0){
            while (cursor.moveToNext()){
                val note=NoteData(cursor.getString(1),cursor.getString(2),cursor.getInt(3))
                notes.add(note)
            }
        }
        cursor.close()
        return notes
    }


}
