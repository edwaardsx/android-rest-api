package com.magdaraog.engagia.ojtapp

//noinspection SuspiciousImport
import android.R
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.magdaraog.engagia.ojtapp.databinding.ActivityDeleteUomBinding

class DeleteUOMActivity : AppCompatActivity() {

    private lateinit var deleteUOMBinding: ActivityDeleteUomBinding

    private lateinit var productsViewModel: ProductsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteUOMBinding = ActivityDeleteUomBinding.inflate(layoutInflater)
        setContentView(deleteUOMBinding.root)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        productsViewModel.initUOMcategs()
        productsViewModel.getUOMcategories()?.observe(this) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                deleteUOMBinding.root.context,
                R.layout.simple_dropdown_item_1line,
                it
            )
            if (it.isNotEmpty()) {
                deleteUOMBinding.btnDeleteUom.isEnabled = true
                deleteUOMBinding.btnDeleteUom.setTextColor(Color.WHITE)
                deleteUOMBinding.atvDeleteUom.isEnabled = true
                deleteUOMBinding.atvDeleteUom.setAdapter(adapter)
                deleteUOMBinding.atvDeleteUom.inputType = 0
            } else {
                deleteUOMBinding.atvDeleteUom.isEnabled = false
                deleteUOMBinding.btnDeleteUom.isEnabled = false
            }
        }

        deleteUOMBinding.btnUOMBack.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        deleteUOMBinding.btnDeleteUom.setOnClickListener {
            productsViewModel.deleteCategory(deleteUOMBinding.atvDeleteUom.text.toString())

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}