package uz.context.noteappwithsharepref.util

import android.annotation.SuppressLint
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Time {

    @SuppressLint("SimpleDateFormat")
    fun timeStamp(): String {
        val timeStamp = Timestamp(System.currentTimeMillis())
        val msh = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
        val time = msh.format(Date(timeStamp.time))

        return time.toString()
    }
}