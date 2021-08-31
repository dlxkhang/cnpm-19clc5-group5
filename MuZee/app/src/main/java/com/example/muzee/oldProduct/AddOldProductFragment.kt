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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentAddOldProductBinding
import com.example.muzee.network.AddOldProduct
import com.example.muzee.oldProduct.add.AddOldProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class AddOldProductFragment : Fragment() {

    var binding: FragmentAddOldProductBinding? = null
    private val viewModel: OldProductViewModel by activityViewModels()
    private val addViewModel: AddOldProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentbinding = FragmentAddOldProductBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding?.lifecycleOwner = this
        binding?.viewModel = addViewModel
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
        addViewModel.status.observe(viewLifecycleOwner,{
            when(it){
                AddOldProductViewModel.ApiStatus.SUCCESS ->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("Add product successful")
                        .setMessage("A new product is added to system")
                        .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                            dialog.cancel()
                            findNavController().popBackStack()
                        }
                    dialog.show()
                }
                AddOldProductViewModel.ApiStatus.EXIST ->{
                    showDiaLog("Add product failed", "This product has been existed in system")
                }
                AddOldProductViewModel.ApiStatus.ERROR ->{
                    showSnackBar()
                }
            }
        })

        return fragmentbinding.root
    }

    private fun showDiaLog(title:String,measage:String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(title)
            .setMessage(measage)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root,R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
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
            inputCondition.error = getString(R.string.error_text_OLD_PRODUCT_CONDITION)
        }
        if(selectCategory!!.editText!!.text!!.isEmpty()){
            success = false
            selectCategory.error = getString(R.string.error_text_CATEGORY)
        }
        if(success){
            val category = (selectCategory.editText as? AutoCompleteTextView)?.text.toString()
            val categoryID = getCategoryID(category)
            val name = inputName.editText?.text.toString()
            val condition = inputCondition.editText?.text.toString().toInt()
            val description = inputDes?.editText?.text.toString()

            if (condition < 0 || condition > 10) {
                Toast.makeText(context, "Condition must be in 1-10", Toast.LENGTH_SHORT).show()
            }
            else {
                val oldProduct = AddOldProduct(null, categoryID, name
                    ,viewModel.NID,null, description, condition)


                addViewModel.addAnOldProduct(oldProduct)
                findNavController().popBackStack()
            }
        }
    }
    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,Category.Drum.name,Category.Electric.name,Category.Guitar.name,Category.Piano.name,Category.Bass.name)
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