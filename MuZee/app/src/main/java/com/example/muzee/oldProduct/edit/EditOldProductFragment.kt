package com.example.muzee.oldProduct.edit

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentEditOldProductBinding
import com.example.muzee.network.AddOldProduct
import com.example.muzee.network.OldProduct
import com.example.muzee.oldProduct.OldProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditOldProductFragment : Fragment() {
    private lateinit var _binding:FragmentEditOldProductBinding
    private val sharedViewModeL: OldProductViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentEditOldProductBinding.inflate(inflater,container,false)
        val binding = fragmentbinding
        binding.lifecycleOwner = this
        val old_product = EditOldProductFragmentArgs.fromBundle(requireArguments()).selectedOldProduct
        val viewModelFractory = EditOldProductViewModelFractory(old_product as OldProduct,application)
        binding.viewModel = ViewModelProvider(this,viewModelFractory).get(EditOldProductViewMoldel::class.java)

        //handle list category
        val textField = binding.labelEditCategory
        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        val index = getListCategory().indexOf(old_product.productCategory.toString())
        (textField.editText as? AutoCompleteTextView)?.setText(getListCategory().get(index),false)

        //handle select image
        val plus_img = binding.editPlusImage
        plus_img.setOnClickListener({
            plus_img.isVisible = false
            dispatchTakePictureIntent()
        })

        _binding = binding

        return fragmentbinding.root
    }
    fun handle_confirm_btn()
    {
        val inputName = _binding.labelEditName
        val selectCategory = _binding.labelEditCategory
        val inputCondition = _binding.labelEditCondition
        val inputDes = _binding?.labelEditProductDescription
        var success = true
        if(inputName.editText!!.text!!.isEmpty()){
            success = false
            inputName.error = getString(R.string.error_text_PRODUCT_NAME)
        }

        if(selectCategory.editText!!.text!!.isEmpty()){
            success = false
            selectCategory.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputCondition.editText!!.text!!.isEmpty()){
            success = false
            inputCondition.error = getString(R.string.error_text_OLD_PRODUCT_CONDITION)
        }
        if(success){
            val category = (selectCategory.editText as? AutoCompleteTextView)?.text.toString()
            val name = inputName.editText?.text.toString()
            val categoryID = getCategoryID(category)

            val condition = inputCondition.editText?.text.toString().toInt()
            val description = inputDes?.editText?.text.toString()



            if (condition < 0 || condition > 10) {
                Toast.makeText(context, "Condition must be in 1-10", Toast.LENGTH_SHORT).show()
            }
            else {
                val oldProduct = AddOldProduct(
                    _binding.viewModel!!.selectedOldProduct.value?.productId, categoryID, name
                    ,sharedViewModeL.NID,_binding.viewModel!!.selectedOldProduct.value?.imageURI, description, condition)

                Toast.makeText(context, "Edit successfully", Toast.LENGTH_SHORT).show()

                sharedViewModeL.editAnOldProduct(oldProduct)
                sharedViewModeL.getOldProducts()

                findNavController().popBackStack()
            }


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@EditOldProductFragment
        }
    }

    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,
            Category.Drum.name,
            Category.Electronic.name,
            Category.Guitar.name,
            Category.Piano.name,
            Category.Bass.name)
    }
    private fun category(str:String): Category {
        return when(str){
            Category.Organ.name ->{
                Category.Organ
            }
            Category.Drum.name ->{
                Category.Drum
            }
            Category.Piano.name ->{
                Category.Piano
            }
            Category.Bass.name ->{
                Category.Bass
            }
            Category.Electronic.name ->{
                Category.Electronic
            }
            else -> Category.Guitar
        }
    }

    private fun getCategoryID(str: String): String {
        return when(str){
            "Piano" -> "001"
            "Guitar" -> "002"
            "Drum" -> "003"
            "Keyboard" -> "004"
            "Bass" -> "005"
            else -> "006"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        _binding.editImageProduct.setImageBitmap(data?.extras?.get("data")as Bitmap)
    }
    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent(){
        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Camera permission")
                .setMessage(e.message)
                .setNeutralButton("Cancel"){dialog,which->
                    dialog.cancel()
                }
        }
    }
}