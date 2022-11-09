package com.magdaraog.engagia.ojtapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.magdaraog.engagia.ojtapp.databinding.ActivityCreateProductBinding

class CreateProductActivity : AppCompatActivity() {

    private lateinit var createBinding: ActivityCreateProductBinding

    private lateinit var productViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding = ActivityCreateProductBinding.inflate(layoutInflater)
        setContentView(createBinding.root)

        productViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]


        createBinding.etCreateProductCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                createProdNameValidation(count)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                createProdNameValidation(count)
            }
        })

        createBinding.etCreateProductName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                createProdCodeValidation(count)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                createProdCodeValidation(count)
            }
        })

        createBinding.btnCreateBack.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        createBinding.btnCreateProduct.setOnClickListener{

            productViewModel.createNewValue(
                createBinding.etCreateProductCode.text.toString(),createBinding.etCreateProductName.text.toString())
            Snackbar.make(
                createBinding.root,
                "Product Created",
                Snackbar.LENGTH_LONG
            ).setAction("View") {
                val intent = Intent(applicationContext, ProductsViewActivity::class.java)

                intent.putExtra("ProdCodeTemp", createBinding.etCreateProductCode.text.toString())


                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }.show()
            clearFields()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun createProdCodeValidation(count: Int){
        if (count > 0 && createBinding.etCreateProductCode.length() > 0){
            createBinding.btnCreateProduct.isEnabled = true
            createBinding.btnCreateProduct.setBackgroundColor(Color.parseColor("#2D3142"))
            createBinding.btnCreateProduct.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            createBinding.btnCreateProduct.isEnabled = false
            createBinding.btnCreateProduct.setBackgroundColor(Color.parseColor("#D6D7D7"))
            createBinding.btnCreateProduct.setTextColor(Color.parseColor("#9F9F9F"))
        }
    }

    fun createProdNameValidation(count: Int){
        if (count > 0 && createBinding.etCreateProductName.length() > 0){
            createBinding.btnCreateProduct.isEnabled = true
            createBinding.btnCreateProduct.setBackgroundColor(Color.parseColor("#2D3142"))
            createBinding.btnCreateProduct.setTextColor(Color.parseColor("#FFFFFF"))

        }else{
            createBinding.btnCreateProduct.isEnabled = false
            createBinding.btnCreateProduct.setBackgroundColor(Color.parseColor("#D6D7D7"))
            createBinding.btnCreateProduct.setTextColor(Color.parseColor("#9F9F9F"))
        }
    }

    private fun clearFields(){
        createBinding.etCreateProductCode.setText("")
        createBinding.etCreateProductName.setText("")
    }
}