package com.magdaraog.engagia.ojtapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.magdaraog.engagia.ojtapp.databinding.ActivityProductsViewBinding
import com.magdaraog.engagia.ojtapp.util.StacktraceUtil

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class ProductsViewActivity : AppCompatActivity() {

    private lateinit var productsViewBinding: ActivityProductsViewBinding
    private lateinit var productsViewModel: ProductsViewModel

    var bundle: Bundle? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productsViewBinding = ActivityProductsViewBinding.inflate(layoutInflater)
        setContentView(productsViewBinding.root)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        bundle = intent.extras

        productsViewModel.initUOM(bundle!!.getString("ProdCodeTemp"))

        productsViewModel.getUOM()?.observe(this) {
            if (it.size !== 0) {
                productsViewBinding.btnEdit.isVisible = true
                productsViewBinding.btnDelete.isVisible = true

                val stk: TableLayout = productsViewBinding.secondTable
                stk.removeAllViews()
                val tbrow1 = TableRow(productsViewBinding.root.context)
                tbrow1.setBackgroundColor(Color.parseColor("#E1E1E1"))
                val h1v = TextView(productsViewBinding.root.context)
                h1v.text = "ID"
                h1v.setPadding(25, 25, 25, 25)
                h1v.setTextColor(Color.parseColor("#000000"))
                h1v.typeface = Typeface.DEFAULT_BOLD
                tbrow1.addView(h1v)
                val lp: TableRow.LayoutParams = h1v.layoutParams as TableRow.LayoutParams
                lp.width = 100
                h1v.layoutParams = lp
                val h2v = TextView(productsViewBinding.root.context)
                h2v.text = "PRODUCT CODE"
                h2v.setPadding(25, 25, 25, 25)
                h2v.setTextColor(Color.parseColor("#000000"))
                h2v.typeface = Typeface.DEFAULT_BOLD
                tbrow1.addView(h2v)
                val h3v = TextView(productsViewBinding.root.context)
                h3v.text = "UNIT OF MEASURE"
                h3v.setPadding(25, 25, 25, 25)
                h3v.setTextColor(Color.parseColor("#000000"))
                h3v.typeface = Typeface.DEFAULT_BOLD
                tbrow1.addView(h3v)
                val h4v = TextView(productsViewBinding.root.context)
                h4v.text = "PRICE"
                h4v.setPadding(25, 25, 25, 25)
                h4v.setTextColor(Color.parseColor("#000000"))
                h4v.typeface = Typeface.DEFAULT_BOLD
                tbrow1.addView(h4v)

                stk.addView(tbrow1)

                for (i in it.indices) {
                    val tbrow = TableRow(productsViewBinding.root.context)
                    val t1v = TextView(productsViewBinding.root.context)
                    t1v.text = it[i].UOMId.toString()
                    t1v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t1v)
                    val t2v = TextView(productsViewBinding.root.context)
                    t2v.text = it[i].UOMCode.toString()
                    t2v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t2v)
                    val t3v = TextView(productsViewBinding.root.context)
                    t3v.text = it[i].UOM.toString()
                    t3v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t3v)
                    val t4v = TextView(productsViewBinding.root.context)
                    t4v.text = it[i].UOMPrice.toString()
                    t4v.setPadding(25, 25, 25, 25)
                    tbrow.addView(t4v)
                    stk.addView(tbrow)
                }
            } else {
                productsViewBinding.btnEdit.isVisible = false
                productsViewBinding.btnDelete.isVisible = false
            }
        }

        constructProductsTable()

        productsViewBinding.btnProductsViewBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        productsViewBinding.btnAdd.setOnClickListener {
            UOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }

        productsViewBinding.btnEdit.setOnClickListener {
            EditUOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }

        productsViewBinding.btnDelete.setOnClickListener {
            DeleteUOMFragment().show(supportFragmentManager, "BottomSheetTag")
            productsViewModel.tempProdCode.value = bundle!!.getString("ProdCodeTemp")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun constructProductsTable(){
        val stk: TableLayout = productsViewBinding.firstTable
        stk.removeAllViews()
        val tbrow1 = TableRow(productsViewBinding.root.context)
        tbrow1.setBackgroundColor(Color.parseColor("#E1E1E1"))
        val h1v = TextView(productsViewBinding.root.context)
        h1v.text = "ID"
        h1v.setPadding(25, 25, 25, 25)
        h1v.setTextColor(Color.parseColor("#FF000000"))
        h1v.typeface = Typeface.DEFAULT_BOLD
        tbrow1.addView(h1v)

        val lp: TableRow.LayoutParams = h1v.layoutParams as TableRow.LayoutParams
        lp.width = 100

        h1v.layoutParams = lp
        val h2v = TextView(productsViewBinding.root.context)
        h2v.text = "PRODUCT CODE"
        h2v.setPadding(25, 25, 25, 25)
        h2v.setTextColor(Color.parseColor("#FF000000"))
        h2v.typeface = Typeface.DEFAULT_BOLD
        tbrow1.addView(h2v)
        val h3v = TextView(productsViewBinding.root.context)
        h3v.text = "PRODUCT NAME"
        h3v.setPadding(25, 25, 25, 25)
        h3v.setTextColor(Color.parseColor("#FF000000"))
        h3v.typeface = Typeface.DEFAULT_BOLD
        tbrow1.addView(h3v)

        stk.addView(tbrow1)

        val tbrow = TableRow(productsViewBinding.root.context)
        val t1v = TextView(productsViewBinding.root.context)
        t1v.text = bundle!!.getString("ProdIDTemp")
        t1v.setPadding(25, 25, 25, 25)
        tbrow.addView(t1v)
        val t2v = TextView(productsViewBinding.root.context)
        t2v.text = bundle!!.getString("ProdCodeTemp")
        t2v.setPadding(25, 25, 25, 25)
        tbrow.addView(t2v)
        val t3v = TextView(productsViewBinding.root.context)
        t3v.text = bundle!!.getString("ProdNameTemp")
        t3v.setPadding(25, 25, 25, 25)
        tbrow.addView(t3v)
        stk.addView(tbrow)
    }
}