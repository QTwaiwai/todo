package com.example.todo.bean

class NoteData() {
    var id:Long = 0
    lateinit var content:String
    lateinit var time:String
    var tag:Int = 0
    constructor(content:String,time:String,tag:Int) : this(){
        this.content=content
        this.time=time
        this.tag=tag
    }
}
