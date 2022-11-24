package com.magdaraog.engagia.ojtapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.magdaraog.engagia.ojtapp.databinding.FragmentBottomSheetBinding
import com.magdaraog.engagia.ojtapp.util.StacktraceUtil

class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var productsViewModel: ProductsViewModel

    private var productID: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        productsViewModel = ViewModelProvider(activity)[ProductsViewModel::class.java]

        productsViewModel.tempProductID.observe(this){
            productID = it
        }

        productsViewModel.tempProdCode.observe(this){
            binding.etProductCodeUpdate.setText(it)
        }

        productsViewModel.tempProdName.observe(this){
            binding.etProductNameUpdate.setText(it)
        }

        binding.btnUpdateProduct.setOnClickListener {
            productsViewModel.editProductValue(productID, binding.etProductCodeUpdate.text.toString(), binding.etProductNameUpdate.text.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }
}