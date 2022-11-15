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

class ProductsAdapter(var context: Context, private var data: List<Products>) : ListAdapter<Products, ProductsAdapter.MyViewHolder>(ProductsDiffUtil()) {

    inner class MyViewHolder(val binding: ActivityRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Products) {
            binding.productsItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val productsItemBinding = ActivityRowLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(productsItemBinding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductsViewActivity::class.java)
            intent.putExtra("ProdIDTemp", data[position].prodID.toString())
            intent.putExtra("ProdCodeTemp", data[position].prodCode)
            intent.putExtra("ProdNameTemp", data[position].prodName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    class ProductsDiffUtil : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.prodID == newItem.prodID
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItems(products: List<Products>) {
        this.data = products
    }
}