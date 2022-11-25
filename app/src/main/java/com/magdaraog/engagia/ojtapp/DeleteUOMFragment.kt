package com.magdaraog.engagia.ojtapp

//noinspection SuspiciousImport
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.magdaraog.engagia.ojtapp.databinding.FragmentDeleteUomBinding

class DeleteUOMFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDeleteUomBinding
    private lateinit var productsViewModel: ProductsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        productsViewModel = ViewModelProvider(activity)[ProductsViewModel::class.java]

        productsViewModel.tempProdCode.observe(this) {
            productsViewModel.initUOMIds(it)
        }

        productsViewModel.getUOMIds()?.observe(this) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(binding.root.context, R.layout.simple_dropdown_item_1line, it)
            if (it.isNotEmpty()) {
                binding.etDeleteProductIdUom.isEnabled = true
                binding.etDeleteProductIdUom.setAdapter(adapter)
                binding.etDeleteProductIdUom.inputType = 0
            } else {
                binding.etDeleteProductIdUom.hint = "No UOM to be selected"
                binding.etDeleteProductIdUom.isEnabled = false
                binding.etDeleteProductIdUom.isEnabled = false
            }
        }

        binding.btnAddUom.setOnClickListener {
            productsViewModel.deleteUOM(binding.etDeleteProductIdUom.text.toString())
            clearFields()
        }
    }

    private fun clearFields(){
        binding.etDeleteProductIdUom.setText("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDeleteUomBinding.inflate(inflater,container,false)
        return binding.root
    }
}