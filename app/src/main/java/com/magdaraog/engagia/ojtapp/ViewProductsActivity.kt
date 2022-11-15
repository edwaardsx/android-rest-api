package com.magdaraog.engagia.ojtapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magdaraog.engagia.ojtapp.databinding.ActivityListOfProductsBinding

class ViewProductsActivity : AppCompatActivity() {

    private lateinit var viewProductsBinding: ActivityListOfProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewProductsBinding = ActivityListOfProductsBinding.inflate(layoutInflater)
        setContentView(viewProductsBinding.root)

        viewProductsBinding.btnUOMBack.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}