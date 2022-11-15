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
import com.magdaraog.engagia.ojtapp.databinding.FragmentAddUomBinding

class UOMFragment : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentAddUomBinding
    private lateinit var productsViewModel: ProductsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        productsViewModel = ViewModelProvider(activity)[ProductsViewModel::class.java]

        productsViewModel.initUOMcategs()

        productsViewModel.tempProdCode.observe(this){
            binding.etProductCodeUom.setText(it)
        }

        productsViewModel.getUOMcategories()?.observe(this) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                binding.root.context,
                R.layout.simple_dropdown_item_1line,
                it
            )
            if (it.isNotEmpty()){
                binding.etUnitsOfMeasureUom.isEnabled = true
                binding.etUnitsOfMeasureUom.setAdapter(adapter)
                binding.etUnitsOfMeasureUom.inputType = 0
            }else{
                binding.etUnitsOfMeasureUom.hint = "No UOM to be selected"
                binding.etUnitsOfMeasureUom.isEnabled = false
                binding.etUnitsOfMeasureUom.isEnabled = false
            }
        }

        binding.btnAddUom.setOnClickListener {
            productsViewModel.createNewUOM(binding.etProductCodeUom.text.toString(), binding.etUnitsOfMeasureUom.text.toString(), binding.etPrice.text.toString())
            clearFields()
        }
    }

    private fun clearFields(){
        binding.etProductCodeUom.setText("")
        binding.etUnitsOfMeasureUom.setText("")
        binding.etPrice.setText("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddUomBinding.inflate(inflater,container,false)
        return binding.root
    }
}