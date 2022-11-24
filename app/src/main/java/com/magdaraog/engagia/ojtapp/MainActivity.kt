package com.magdaraog.engagia.ojtapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.magdaraog.engagia.ojtapp.databinding.ActivityMainBinding
import com.magdaraog.engagia.ojtapp.helper.MyButton
import com.magdaraog.engagia.ojtapp.helper.MySwipeHelper
import com.magdaraog.engagia.ojtapp.listener.MyButtonClickListener
import com.magdaraog.engagia.ojtapp.util.StacktraceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var productIDList = mutableListOf<String>()
    private var productCodeList = mutableListOf<String>()
    private var productNameList = mutableListOf<String>()

    lateinit var mainBinding: ActivityMainBinding

    private var data = listOf(Products(prodID = null, prodCode = null, prodName = null))

    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var recyclerAdapter: ProductsAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        supportActionBar?.title = "{ ... } REST:API"
        mainBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))


        //For error tracking testing (DISABLE IF NOT NEEDED) you can directly check logs after Application start
        try {
            var a: List<String> = ArrayList()

            var b = a[10]
        }catch (e: Exception)
        {
            StacktraceUtil().saveStackTrace(applicationContext, e)
        }
        //For error tracking testing (DISABLE IF NOT NEEDED) you can directly check logs after Application start

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        productsViewModel.init()

        productsViewModel.getProducts()?.observe(this) {

            if (it.size != 0)
            {
                mainBinding.npts.visibility = View.GONE
            }else{
                mainBinding.npts.visibility = View.VISIBLE
            }

            linkLists(productsViewModel)
            recyclerAdapter.setItems(it)
            recyclerAdapter.notifyDataSetChanged()
        }

        initRecyclerView()
        StrictMode.enableDefaults()

        object : MySwipeHelper(mainBinding.root.context, mainBinding.recyclerView, 300) {
            override fun instantiateMyButton(viewHolder: RecyclerView.ViewHolder, buffer: MutableList<MyButton>) {
                buffer.add(MyButton(mainBinding.root.context,
                        "Delete",
                        40,
                        0,
                        Color.parseColor("#FF3C30"), object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                val job1 = CoroutineScope(IO).launch {
                                    productsViewModel.deleteProductValue(productIDList[pos])
                                }
                                runBlocking {
                                    job1.join()
                                    Snackbar.make(mainBinding.root, productNameList[pos] + " Deleted", Snackbar.LENGTH_LONG).setAction("Undo") {
                                        println("Undo clicked")
                                    }.show()
                                    removeFromLists(pos)
                                    linkLists(productsViewModel)
                                    recyclerAdapter.notifyItemRemoved(pos)

                                    if (recyclerAdapter.itemCount != 0)  {
                                        mainBinding.npts.visibility = View.GONE
                                    } else {
                                        mainBinding.npts.visibility = View.VISIBLE
                                    }


                                }
                            }
                        }))
                buffer.add(MyButton(mainBinding.root.context,
                        "Edit",
                        40,
                        0,
                        Color.parseColor("#FF9502"), object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                BottomSheet().show(supportFragmentManager, "BottomSheetTag")
                                productsViewModel.tempProductID.value = productIDList[pos]
                                productsViewModel.tempProdCode.value = productCodeList[pos]
                                productsViewModel.tempProdName.value = productNameList[pos]
                            }
                        })
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        productsViewModel.getProducts()?.observe(this) {
            if (it.size != 0)  {
                mainBinding.npts.visibility = View.GONE
            } else {
                mainBinding.npts.visibility = View.VISIBLE
            }
            linkLists(productsViewModel)
            recyclerAdapter.setItems(it)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_create -> {
                val intent = Intent(applicationContext, CreateProductActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.action_create_uom -> {
                val intent = Intent(applicationContext, UOMActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.action_delete_uom -> {
                val intent = Intent(applicationContext, DeleteUOMActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.action_view_list_of_producsts -> {
                val intent = Intent(applicationContext, ViewProductsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.action_logs -> {
                val intent = Intent(applicationContext, LogsActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun linkLists(PVmodel : ProductsViewModel) {
        val currentProducts: List<Products>? =  PVmodel.getProducts()?.value
        clearLists()
        for (i in 0 until currentProducts!!.size) {
            currentProducts[i].prodCode?.let {
                currentProducts[i].prodName?.let { it1 ->
                    currentProducts[i].prodID?.let { it2 ->
                        addToList(
                            it2,
                            it, it1
                        )
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        mainBinding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(applicationContext)
            layoutManager = linearLayoutManager
            recyclerAdapter = ProductsAdapter(mainBinding.root.context, data)
            adapter = recyclerAdapter
        }
    }

    private fun clearLists() {
        productIDList.clear()
        productCodeList.clear()
        productNameList.clear()
    }

    private fun removeFromLists(pos:Int) {
        productIDList.removeAt(pos)
        productCodeList.removeAt(pos)
        productNameList.removeAt(pos)
    }

    private fun addToList(id: Int, code: String, name: String) {
        productIDList.add(id.toString())
        productCodeList.add(code)
        productNameList.add(name)
    }
}