package uz.context.noteappwithsharepref.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.context.noteappwithsharepref.R
import uz.context.noteappwithsharepref.adapter.RecyclerViewAdapter
import uz.context.noteappwithsharepref.databinding.ActivityMainBinding
import uz.context.noteappwithsharepref.manager.PrefsManager
import uz.context.noteappwithsharepref.model.Note
import uz.context.noteappwithsharepref.util.Time
import uz.context.noteappwithsharepref.util.toast


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var prefsManager: PrefsManager
    private lateinit var linearLayout: LinearLayout
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var bin: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)

        initViews()

    }

    private fun initViews() {
        linearLayout = findViewById(R.id.linear_layout)
        prefsManager = PrefsManager(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recyclerViewAdapter = RecyclerViewAdapter(getAllNote())
        recyclerView.adapter = recyclerViewAdapter

        val addBtn: FloatingActionButton = findViewById(R.id.floating_fab)

        addBtn.setOnClickListener {
            showDialog()
        }
        linearLayout.isVisible = recyclerViewAdapter.lists.isEmpty()
    }

    private fun showDialog() {
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.alert_layout, null)
        val dialog = AlertDialog.Builder(this).create()
        dialog.setView(view)
        val editText: EditText = view.findViewById(R.id.edit_alert)
        val btnSave: MaterialButton = view.findViewById(R.id.btn_save)
        val btnCancel: MaterialButton = view.findViewById(R.id.btn_cancel)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnSave.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                toast("Please enter some note!")
            } else {
                recyclerViewAdapter.addNote(Note(Time.timeStamp(), text))
                prefsManager.saveNote(recyclerViewAdapter.lists)
                linearLayout.isVisible = recyclerViewAdapter.lists.isEmpty()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                prefsManager.clearAllNote()
                toast("Deleted All Notes!")
                intent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllNote(): ArrayList<Note> {
        return prefsManager.getNote() ?: return arrayListOf()
    }

    private fun intent() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}