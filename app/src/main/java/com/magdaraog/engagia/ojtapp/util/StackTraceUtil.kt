package com.magdaraog.engagia.ojtapp.util

import android.content.Context
import android.net.TrafficStats
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.release.gfg1.DBHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class StacktraceUtil {

    //host
    private var ipAddress: String = "192.168.100.7:8765"
    private var insertLogsURL = "http://$ipAddress/productsTable/insertLogs.json"

    private lateinit var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler

    private var context: Context? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveStackTrace(context: Context, e:Exception) {

        this.context = context

        var context: Context

        val db = DBHelper(this.context!!, null)

        var logs = "test"

        var dateFetcher = LocalDate.now().format(DateTimeFormatter.ofPattern("LLL dd, yyyy"))

        var cal: Calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("KK:mm aaa")
        var currentTime = simpleDateFormat.format(cal.time)

        db.addLog(Log.getStackTraceString(e), dateFetcher.toString(), currentTime.toString())

        //defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

        /*Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            saveLog(e)


        }*/
    }

    private fun saveLog(exception: Throwable) {
        val job = CoroutineScope(Dispatchers.IO).launch {

            var stackTrace: String = Log.getStackTraceString(exception)

            //httpPostForLogs(insertLogsURL,stackTrace)

        }
    }

    /*//register uncaught exception handler
    fun initStacktraceLogging() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()!!
        Thread.setDefaultUncaughtExceptionHandler {
                _, e ->
            saveLog(e)
        }
    }

    //pass throwable data to httpPostLogs function for sending to database
    private fun saveLog(exception: Throwable) {
        CoroutineScope(Dispatchers.IO).launch {
            val stackTrace: String = Log.getStackTraceString(exception)
            httpPostForLogs(insertLogsURL,stackTrace)
        }
    }

    //network call to POST stacktrace data to database
    private fun httpPostForLogs(myURL: String?, log: String?): String {
        val result:String
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        val url = URL(myURL)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.readTimeout = 30000
        urlConnection.connectTimeout = 30000
        val builder: Uri.Builder = Uri.Builder().appendQueryParameter("log", log)
        val query: String? = builder.build().encodedQuery
        urlConnection.requestMethod = "POST"
        urlConnection.doInput = true
        urlConnection.doOutput = true
        urlConnection.connect()
        val wr = DataOutputStream(urlConnection.outputStream)
        wr.writeBytes(query)
        wr.flush()
        wr.close()
        val isNew: InputStream = urlConnection.inputStream
        BufferedReader(InputStreamReader(isNew))
        result = convertInputStreamToString(isNew)
        return result
    }

    //for debugging purposes
    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line:String? = bufferedReader.readLine()
        var result = ""
        while (line != null) {
            result += line
            line = bufferedReader.readLine()
        }
        inputStream.close()
        return result
    }*/
}