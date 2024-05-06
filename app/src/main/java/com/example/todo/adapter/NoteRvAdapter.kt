package com.example.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.CRUD
import com.example.todo.R
import com.example.todo.bean.NoteData
import java.util.Collections

class NoteRvAdapter(context: Context, noteList: MutableList<NoteData>) :Filterable,
    RecyclerView.Adapter<NoteRvAdapter.SimpleViewHolder>(), ItemTouchHelperAdapter{
    private val mContext:Context = context
    private val backList=noteList//备份数据
    private  var mNoteList: MutableList<NoteData> = noteList
    private lateinit var mFilter: MyFilter

    private var mItemClick: ((Int) -> Unit?)? = null


    fun setOnItemClickListener(cl: ((Int) -> Unit?)) {
        this.mItemClick = cl
         }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        mContext.setTheme(R.style.Base_Theme_Todo)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_list, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {

        holder.bind(mNoteList[position])
    }

    override fun getItemCount() = mNoteList.size

    inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mTvContent: TextView = view.findViewById<TextView?>(R.id.tv_item_content).apply {
            setOnClickListener{
                mItemClick?.invoke(adapterPosition)
            }
        }
        private val mTvDate: TextView = view.findViewById(R.id.tv_item_date)
        fun bind(note: NoteData) {
            mTvContent.text = note.content
            mTvDate.text = note.time
        }


    }

    override fun getFilter(): Filter =mFilter


    inner class MyFilter: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result=FilterResults()
            val list: MutableList<NoteData>
            if(TextUtils.isEmpty(constraint)){
                list=backList
            }else{
                list= mutableListOf()
                for (note in backList){
                    if(constraint?.let { note.content.contains(it) } == true){
                        list.add(note)
                    }
                }
            }
            result.values=list
            result.count=list.size
            return result
        }


        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mNoteList= results?.values as MutableList<NoteData>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
               notifyDataSetChanged()
            }
        }

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
         Collections.swap(mNoteList,fromPosition,toPosition)
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        val op= CRUD(mContext)
        op.open()
        op.deleteNote(mNoteList[position])
        notifyItemRemoved(position)
        mNoteList.removeAt(position)
    }
}