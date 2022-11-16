package com.magdaraog.engagia.ojtapp

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.magdaraog.engagia.ojtapp.databinding.ActivityUomBinding

class UOMActivity : AppCompatActivity() {

    private lateinit var uomBinding: ActivityUomBinding
    private lateinit var productViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uomBinding = ActivityUomBinding.inflate(layoutInflater)
        setContentView(uomBinding.root)

        productViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productViewModel.saveStackTrace()

        uomBinding.etUom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0 ){
                    uomBinding.btnAddUom.isEnabled = true
                    uomBinding.btnAddUom.setTextColor(Color.WHITE)
                }else{
                    uomBinding.btnAddUom.isEnabled = false
                    uomBinding.btnAddUom.setTextColor(Color.parseColor("#9F9F9F"))
                }
            }
        })

        uomBinding.btnUOMBack.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        uomBinding.btnAddUom.setOnClickListener{
            productViewModel.createNewUOMcateg(uomBinding.etUom.text.toString())
            clearFields()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun clearFields() {
        uomBinding.etUom.setText("")
    }
}