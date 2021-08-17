package com.example.muzee.seller.product_overview

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.data.newProduct
import com.example.muzee.data.oldProduct
import com.example.muzee.databinding.FragmentAddNewProductBinding
import com.example.muzee.oldProduct.OldProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AddNewProductFragment : Fragment() {

    private var binding: FragmentAddNewProductBinding? = null // binding fragment_add_new_product.xml
    private val viewModel: SellerProductOverviewViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup data binding
        val fragmentBinding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val activity = activity as AppCompatActivity? // get activity
        activity!!.supportActionBar?.setTitle("Add New Product") // set title text for seller product screen
        val textField = binding?.labelSelectCategory

        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField?.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val btn_confirm = binding?.btnConfirmAddNewProduct
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
        binding?.labelInputPrice?.editText?.doOnTextChanged{text, start, before, count ->

            binding!!.labelInputPrice.error = null

        }
        binding?.labelSelectCategory?.editText?.doOnTextChanged{text, start, before, count ->
            binding!!.labelSelectCategory.error = null
        }
        return fragmentBinding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding?.addImageProduct?.setImageBitmap(data?.extras?.get("data")as Bitmap)
    }
    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Camera permission")
                .setMessage(e.message)
                .setNeutralButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
        }
    }
    private fun handle_confirm_btn()
    {
        val inputName = binding?.labelInputName
        val inputPrice = binding?.labelInputPrice
        val selectCategory = binding?.labelSelectCategory
        val inputStock = binding?.labelInputStockNewProduct
        var success = true
        if(inputName!!.editText!!.text!!.isEmpty()){
            success = false
            inputName.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputPrice!!.editText!!.text!!.isEmpty()){
            success = false
            inputPrice.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(selectCategory!!.editText!!.text!!.isEmpty()){
            success = false
            selectCategory.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputStock!!.editText!!.text!!.isEmpty()){
            success = false
            inputStock.error = getString(R.string.error_text_OLD_PRODUCT_CONDITION)
        }
        if(success){
            val category = category((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val condition = inputStock.editText?.text.toString().toInt()
            val sellerName = "huy"
            val newProduct = newProduct(category,name,price,sellerName,condition)
            viewModel.addAnNewProduct(newProduct)
            findNavController().navigate(R.id.action_addNewProductFragment_to_sellerProductOverviewFragment)
        }
    }
    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,Category.Drum.name,Category.Electronic.name,Category.Guitar.name,Category.Piano.name,Category.Bass.name)
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
    /*
    var binding: FragmentAddOldProductBinding? = null
    private val viewModel: OldProductViewModel by viewModels()
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
        binding?.labelInputPrice?.editText?.doOnTextChanged{text, start, before, count ->

            binding!!.labelInputPrice.error = null

        }
        binding?.labelSelectCategory?.editText?.doOnTextChanged{text, start, before, count ->
            binding!!.labelSelectCategory.error = null
        }

        return fragmentbinding.root
    }
    private fun handle_confirm_btn()
    {
        val inputName = binding?.labelInputName
        val inputPrice = binding?.labelInputPrice
        val selectCategory = binding?.labelSelectCategory
        val inputCondition = binding?.labelInputCondition
        var success = true
        if(inputName!!.editText!!.text!!.isEmpty()){
            success = false
            inputName.error = getString(R.string.error_text_PRODUCT_NAME)
        }
        if(inputPrice!!.editText!!.text!!.isEmpty()){
            success = false
            inputPrice.error = getString(R.string.error_text_PRODUCT_NAME)
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
            val category = category((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val condition = inputCondition.editText?.text.toString().toInt()
            val sellerName = "huy"
            val oldProduct = oldProduct(category,name,price,sellerName,condition)
            viewModel.addAnOldProduct(oldProduct)
            findNavController().navigate(R.id.action_addOldProductFragment_to_oldProductStoreFragment)
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
     */
}