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
import com.magdaraog.engagia.ojtapp.databinding.FragmentEditUomBinding

class EditUOMFragment : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentEditUomBinding
    private lateinit var productsViewModel: ProductsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        productsViewModel = ViewModelProvider(activity)[ProductsViewModel::class.java]
        productsViewModel.saveStackTrace()

        productsViewModel.initUOMcategs()

        productsViewModel.tempProdCode.observe(this){
            productsViewModel.initUOMIds(it)
            binding.etEditProductCodeUom.setText(it)
        }

        productsViewModel.getUOMIds()?.observe(this){
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                binding.root.context,
                R.layout.simple_dropdown_item_1line,
                it
            )
            if (it.isNotEmpty()){
                binding.etEditProductIdUom.isEnabled = true
                binding.etEditProductIdUom.setAdapter(adapter)
                binding.etEditProductIdUom.inputType = 0
            }else{
                binding.etEditProductIdUom.hint = "No UOM to be selected"
                binding.etEditProductIdUom.isEnabled = false
                binding.etEditProductIdUom.isEnabled = false
            }
        }

        productsViewModel.getUOMcategories()?.observe(this) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                binding.root.context,
                R.layout.simple_dropdown_item_1line,
                it
            )
            if (it.isNotEmpty()){
                binding.etEditUom.isEnabled = true
                binding.etEditUom.setAdapter(adapter)
                binding.etEditUom.inputType = 0
            }else{
                binding.etEditUom.hint = "No UOM to be selected"
                binding.etEditUom.isEnabled = false
                binding.etEditUom.isEnabled = false
            }
        }

        binding.btnAddUom.setOnClickListener {
            productsViewModel.editUOMValue(binding.etEditProductIdUom.text.toString() , binding.etEditProductCodeUom.text.toString(), binding.etEditUom.text.toString(), binding.etEditUomPrice.text.toString())
            clearFields()
        }
    }

    private fun clearFields(){
        binding.etEditProductIdUom.setText("")
        binding.etEditProductCodeUom.setText("")
        binding.etEditUom.setText("")
        binding.etEditUomPrice.setText("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditUomBinding.inflate(inflater,container,false)
        return binding.root
    }
}