package com.magdaraog.engagia.ojtapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.magdaraog.engagia.ojtapp.databinding.ActivityLogsBinding
import com.magdaraog.engagia.ojtapp.sqlite.DBHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class LogsActivity : AppCompatActivity() {

    private lateinit var logsBinding: ActivityLogsBinding
    private lateinit var recyclerAdapter: LogsAdapter

    private var data = ArrayList<Logs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logsBinding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(logsBinding.root)

        initRecyclerView()
        initData()

        logsBinding.btnLogsBack.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

    }

    private fun initRecyclerView() {
        logsBinding.recyclerViewLogs.apply {
            val linearLayoutManager = LinearLayoutManager(applicationContext)
            layoutManager = linearLayoutManager
            recyclerAdapter = LogsAdapter(logsBinding.root.context, data)
            adapter = recyclerAdapter
        }
    }

    @SuppressLint("Range", "NotifyDataSetChanged")
    private fun initData() {
        val job1 = CoroutineScope(IO).launch {
            val db = DBHelper(logsBinding.root.context, null)
            val cursor = db.getLog()
            if (cursor?.count != 0) {
                cursor?.moveToFirst()
                data.add(Logs(cursor?.getString(cursor.getColumnIndex(DBHelper.ID_COL))?.toInt(),
                    cursor?.getString(cursor.getColumnIndex(DBHelper.LOG_COL)),
                    cursor?.getString(cursor.getColumnIndex(DBHelper.DATE_COL)),
                    cursor?.getString(cursor.getColumnIndex(DBHelper.TIME_COL))
                ))
                while (cursor!!.moveToNext())
                {
                    data.add(Logs(
                        cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))?.toInt(),
                        cursor.getString(cursor.getColumnIndex(DBHelper.LOG_COL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.TIME_COL))
                    ))
                }
                cursor.close()
            }
        }
        runBlocking {
            job1.join()
            recyclerAdapter.setItems(data)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}