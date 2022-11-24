package com.magdaraog.engagia.ojtapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.magdaraog.engagia.ojtapp.databinding.ActivityRowLayoutBinding
import com.magdaraog.engagia.ojtapp.databinding.ActivityRowLogsLayoutBinding

class LogsAdapter (var context: Context, private var data: List<Logs>) : ListAdapter<Logs, LogsAdapter.MyViewHolder>(LogsDiffUtil()) {

    inner class MyViewHolder(val binding: ActivityRowLogsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Logs) {
            binding.logsItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val productsItemBinding = ActivityRowLogsLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(productsItemBinding)

    }

    override fun onBindViewHolder(holder: LogsAdapter.MyViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {

            val intent = Intent(context, LogsViewActivity::class.java)

            intent.putExtra("LogsTemp", data[position].log)
            intent.putExtra("LogsDateTemp", data[position].logsDate)
            intent.putExtra("LogsTimeTemp", data[position].logsTime)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }
        val incrementedPosition = position + 1
        holder.binding.index = incrementedPosition.toString()
    }

    class LogsDiffUtil : DiffUtil.ItemCallback<Logs>() {
        override fun areItemsTheSame(oldItem: Logs, newItem: Logs): Boolean {
            return oldItem.logsID == newItem.logsID
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Logs, newItem: Logs): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(logs: List<Logs>) {
        this.data = logs

        println("data received: ${this.data}")

    }
}