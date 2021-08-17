package com.example.muzee.seller.edit_new_product

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.data.newProduct
import com.example.muzee.databinding.FragmentEditNewProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditNewProductFragment : Fragment() {
    private lateinit var binding:FragmentEditNewProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentEditNewProductBinding.inflate(inflater,container,false)
        val binding = fragmentbinding
        binding.lifecycleOwner = this
        val new_product = EditNewProductFragmentArgs.fromBundle(requireArguments()).selectedNewProduct
        val viewModelFractory = EditNewProductViewModelFractory(new_product as newProduct,application)
        binding.viewModel = ViewModelProvider(this,viewModelFractory).get(EditNewProductViewMoldel::class.java)
        //handle list category
        val textField = binding.labelEditCategory
        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //handle select image
        val plus_img = binding.editPlusImage
        plus_img.setOnClickListener({
            plus_img.isVisible = false
            dispatchTakePictureIntent()
        })

        // handle confirm button
        binding.btnConfirmEditProduct.setOnClickListener{
            handle_confirm_btn()
        }
        return fragmentbinding.root
    }
    private fun handle_confirm_btn()
    {
        val inputName = binding.labelEditName
        val inputPrice = binding.labelEditPrice
        val selectCategory = binding.labelEditCategory
        val inputStock = binding.labelEditStock
        var success = true
        if(inputName.editText!!.text!!.isEmpty()){
            success = false
            inputName.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputPrice.editText!!.text!!.isEmpty()){
            success = false
            inputPrice.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(selectCategory.editText!!.text!!.isEmpty()){
            success = false
            selectCategory.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputStock.editText!!.text!!.isEmpty()){
            success = false
            inputStock.error = getString(R.string.error_text_OLD_PRODUCT_CONDITION)
        }
        if(success){
            val category = category((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val stock = inputStock.editText?.text.toString().toInt()
            val sellerName = "huy"
            val newProduct = newProduct("552",category,name,price,sellerName,stock)

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.editImageProduct.setImageBitmap(data?.extras?.get("data")as Bitmap)
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