package com.magdaraog.engagia.ojtapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.magdaraog.engagia.ojtapp.databinding.ActivityLogsBinding
import com.magdaraog.engagia.ojtapp.databinding.ActivityLogsViewBinding

class LogsViewActivity : AppCompatActivity() {

    private lateinit var logsViewBinding: ActivityLogsViewBinding

    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs_view)

        logsViewBinding = ActivityLogsViewBinding.inflate(layoutInflater)
        setContentView(logsViewBinding.root)

        bundle = intent.extras

        logsViewBinding.logsView.text = bundle!!.getString("LogsTemp")
        logsViewBinding.logsViewDate.text = bundle!!.getString("LogsDateTemp")
        logsViewBinding.logsViewTime.text = bundle!!.getString("LogsTimeTemp")

    }
}