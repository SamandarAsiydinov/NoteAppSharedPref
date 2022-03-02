package uz.context.noteappwithsharepref.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.context.noteappwithsharepref.R
import uz.context.noteappwithsharepref.model.Note
import uz.context.noteappwithsharepref.util.Time

class RecyclerViewAdapter(
    val lists: ArrayList<Note>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val note = lists[position]
        if (holder is MyViewHolder) {
            holder.apply {
                textTime.text = note.time
                textNote.text = note.note
            }
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTime: TextView = view.findViewById(R.id.text_time)
        val textNote: TextView = view.findViewById(R.id.text_note)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(note: Note) {
        lists.add(note)
        notifyDataSetChanged()
    }
}