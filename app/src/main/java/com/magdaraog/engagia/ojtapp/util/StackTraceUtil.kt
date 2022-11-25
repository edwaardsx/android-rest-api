package com.magdaraog.engagia.ojtapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.magdaraog.engagia.ojtapp.sqlite.DBHelper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class StacktraceUtil {

    private var context: Context? = null

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveStackTrace(context: Context, e:Exception) {
        this.context = context
        val db = DBHelper(this.context!!, null)
        val dateFetcher = LocalDate.now().format(DateTimeFormatter.ofPattern("LLL dd, yyyy"))
        val cal: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("KK:mm aaa")
        val currentTime = simpleDateFormat.format(cal.time)
        db.addLog(Log.getStackTraceString(e), dateFetcher.toString(), currentTime.toString())
    }
}