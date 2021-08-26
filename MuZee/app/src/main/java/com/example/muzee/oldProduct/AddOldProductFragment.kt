package com.example.muzee.oldProduct

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
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentAddOldProductBinding
import com.example.muzee.network.AddOldProduct
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AddOldProductFragment : Fragment() {

    var binding: FragmentAddOldProductBinding? = null
    private val viewModel: OldProductViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentbinding = FragmentAddOldProductBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding?.viewModel = viewModel
        val textField = binding?.labelSelectCategory

        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField?.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val btn_confirm = binding?.btnConfirmAddProduct
        btn_confirm?.setOnClickListener {
            handle_confirm_btn()
        }
        val plus_img = binding?.plusImage

        plus_img?.setOnClickListener({
            plus_img.isVisible = false
            dispatchTakePictureIntent()
        })
        binding?.labelInputName?.editText?.doOnTextChanged{text, start, before, count ->
                binding!!.labelInputName.error = null

        }
        binding?.labelInputCondition?.editText?.doOnTextChanged{text, start, before, count ->

            binding!!.labelInputCondition.error = null

        }
        binding?.labelSelectCategory?.editText?.doOnTextChanged{text, start, before, count ->
            binding!!.labelSelectCategory.error = null
        }

        return fragmentbinding.root
    }
    private fun handle_confirm_btn()
    {
        val inputName = binding?.labelInputName
        val inputCondition = binding?.labelInputCondition
        val selectCategory = binding?.labelSelectCategory
        val inputDes = binding?.labelProductDescription
        var success = true
        if(inputName!!.editText!!.text!!.isEmpty()){
            success = false
            inputName.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputCondition!!.editText!!.text!!.isEmpty()){
            success = false
            inputCondition.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(selectCategory!!.editText!!.text!!.isEmpty()){
            success = false
            selectCategory.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputCondition!!.editText!!.text!!.isEmpty()){
            success = false
            inputCondition.error = getString(R.string.error_text_OLD_PRODUCT_CONDITION)
        }
        if(success){
            val category = (selectCategory.editText as? AutoCompleteTextView)?.text.toString()
            val categoryID = getCategoryID(category)
            val name = inputName.editText?.text.toString()
            val condition = inputCondition.editText?.text.toString().toInt()
            val description = inputDes?.editText?.text.toString()

            val oldProduct = AddOldProduct(null, categoryID, name
                ,viewModel.NID,null, description, condition)

            viewModel.editAnOldProduct(oldProduct)
            Toast.makeText(context, "Edit successfully", Toast.LENGTH_LONG).show()
            findNavController().popBackStack(R.id.oldProductStoreFragment, false)
        }
    }
    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,Category.Drum.name,Category.Electronic.name,Category.Guitar.name,Category.Piano.name,Category.Bass.name)
    }
    private fun category(str:String):Category{
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
        binding?.addImageProduct?.setImageBitmap(data?.extras?.get("data")as Bitmap)
    }
    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent(){
        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e:ActivityNotFoundException){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Camera permission")
                .setMessage(e.message)
                .setNeutralButton("Cancel"){dialog,which->
                    dialog.cancel()
                }
        }
    }

}