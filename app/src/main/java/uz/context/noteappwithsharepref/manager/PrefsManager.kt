package uz.context.noteappwithsharepref.manager

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.context.noteappwithsharepref.model.Note
import uz.context.noteappwithsharepref.util.Constants
import java.lang.reflect.Type

class PrefsManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE)


    private val gson = Gson()

    fun saveNote(note: ArrayList<Note>) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.MY_KEY, gson.toJson(note))
        editor.apply()
    }

    fun getNote(): ArrayList<Note>? {
        val json = sharedPreferences.getString(Constants.MY_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Note>>() {}.type

        return gson.fromJson(json, type)
    }

    fun clearAllNote() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}