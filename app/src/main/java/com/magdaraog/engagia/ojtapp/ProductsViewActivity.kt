package com.magdaraog.engagia.ojtapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.magdaraog.engagia.ojtapp.databinding.ActivityMainBinding
import com.magdaraog.engagia.ojtapp.databinding.ActivityProductsViewBinding
import org.json.JSONObject

class ProductsViewActivity : AppCompatActivity() {

    private lateinit var _ProductsViewBinding: ActivityProductsViewBinding
    private lateinit var productsViewModel: ProductsViewModel

    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _ProductsViewBinding = ActivityProductsViewBinding.inflate(layoutInflater)
        setContentView(_ProductsViewBinding.root)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        bundle = intent.extras

        productsViewModel.initUOM(bundle!!.getString("ProdCodeTemp"))

        productsViewModel.getUOM()?.observe(this, Observer {
            if (it.size !== 0){
                _ProductsViewBinding.btnEdit.isVisible = true
                _ProductsViewBinding.btnDelete.isVisible = true

                val stk: TableLayout = _ProductsViewBinding.secondTable
                stk.removeAllViews()
                val tbrow1: TableRow = TableRow(_ProductsViewBinding.root.context)
                tbrow1.setBackgroundColor(Color.parseColor("#E1E1E1"))
                val h1v: TextView = TextView(_ProductsViewBinding.root.context)
                h1v.setText("ID")
                h1v.setPadding(25, 25, 25, 25)
                h1v.setTextColor(Color.parseColor("#000000"))
                h1v.setTypeface(Typeface.DEFAULT_BOLD)
                tbrow1.addView(h1v)
                val lp: TableRow.LayoutParams = h1v.getLayoutParams() as TableRow.LayoutParams
                lp.width = 100
                h1v.setLayoutParams(lp)
                val h2v: TextView = TextView(_ProductsViewBinding.root.context)
                h2v.setText("PRODUCT CODE")
                h2v.setPadding(25, 25, 25, 25)
                h2v.setTextColor(Color.parseColor("#000000"))
                h2v.setTypeface(Typeface.DEFAULT_BOLD)
                tbrow1.addView(h2v)
                val h3v: TextView = TextView(_ProductsViewBinding.root.context)
                h3v.setText("UNIT OF MEASURE")
                h3v.setPadding(25, 25, 25, 25)
                h3v.setTextColor(Color.parseColor("#000000"))
                h3v.setTypeface(Typeface.DEFAULT_BOLD)
                tbrow1.addView(h3v)
                val h4v: TextView = TextView(_ProductsViewBinding.root.context)
                h4v.setText("PRICE")
                h4v.setPadding(25, 25, 25, 25)
                h4v.setTextColor(Color.parseColor("#000000"))
                h4v.setTypeface(Typeface.DEFAULT_BOLD)
                tbrow1.addView(h4v)

                stk.addView(tbrow1)

                for (i in 0 until it.size) {
                    val tbrow: TableRow = TableRow(_ProductsViewBinding.root.context)
                    val t1v: TextView = TextView(_ProductsViewBinding.root.context)
                    t1v.setText(it[i].UOMId.toString())
                    t1v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t1v)
                    val t2v: TextView = TextView(_ProductsViewBinding.root.context)
                    t2v.setText(it[i].UOMCode.toString())
                    t2v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t2v)
                    val t3v: TextView = TextView(_ProductsViewBinding.root.context)
                    t3v.setText(it[i].UOM.toString())
                    t3v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t3v)
                    val t4v: TextView = TextView(_ProductsViewBinding.root.context)
                    t4v.setText(it[i].UOMPrice.toString())
                    t4v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t4v)
                    stk.addView(tbrow)
                }
            }else{
                _ProductsViewBinding.btnEdit.isVisible = false
                _ProductsViewBinding.btnDelete.isVisible = false
            }
        })

        constructProductsTable()

        _ProductsViewBinding.btnProductsViewBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        _ProductsViewBinding.btnAdd.setOnClickListener {
            UOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }

        _ProductsViewBinding.btnEdit.setOnClickListener {
            EditUOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }

        _ProductsViewBinding.btnDelete.setOnClickListener {
            DeleteUOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }
    }

    private fun constructProductsTable(){
        val stk: TableLayout = _ProductsViewBinding.firstTable
        stk.removeAllViews()
        val tbrow1: TableRow = TableRow(_ProductsViewBinding.root.context)
        tbrow1.setBackgroundColor(Color.parseColor("#E1E1E1"))
        val h1v: TextView = TextView(_ProductsViewBinding.root.context)
        h1v.setText("ID")
        h1v.setPadding(25, 25, 25, 25)
        h1v.setTextColor(Color.parseColor("#FF000000"))
        h1v.setTypeface(Typeface.DEFAULT_BOLD)
        tbrow1.addView(h1v)

        val lp: TableRow.LayoutParams = h1v.getLayoutParams() as TableRow.LayoutParams
        lp.width = 100

        h1v.setLayoutParams(lp)
        val h2v: TextView = TextView(_ProductsViewBinding.root.context)
        h2v.setText("PRODUCT CODE")
        h2v.setPadding(25, 25, 25, 25)
        h2v.setTextColor(Color.parseColor("#FF000000"))
        h2v.setTypeface(Typeface.DEFAULT_BOLD)
        tbrow1.addView(h2v)
        val h3v: TextView = TextView(_ProductsViewBinding.root.context)
        h3v.setText("PRODUCT NAME")
        h3v.setPadding(25, 25, 25, 25)
        h3v.setTextColor(Color.parseColor("#FF000000"))
        h3v.setTypeface(Typeface.DEFAULT_BOLD)
        tbrow1.addView(h3v)

        stk.addView(tbrow1)

        val tbrow: TableRow = TableRow(_ProductsViewBinding.root.context)
        val t1v: TextView = TextView(_ProductsViewBinding.root.context)
        t1v.setText(bundle!!.getString("ProdIDTemp"))
        t1v.setPadding(25, 25, 25, 25)
        tbrow.addView(t1v)
        val t2v: TextView = TextView(_ProductsViewBinding.root.context)
        t2v.setText(bundle!!.getString("ProdCodeTemp"))
        t2v.setPadding(25, 25, 25, 25)
        tbrow.addView(t2v)
        val t3v: TextView = TextView(_ProductsViewBinding.root.context)
        t3v.setText(bundle!!.getString("ProdNameTemp"))
        t3v.setPadding(25, 25, 25, 25)
        tbrow.addView(t3v)
        stk.addView(tbrow)
    }
}