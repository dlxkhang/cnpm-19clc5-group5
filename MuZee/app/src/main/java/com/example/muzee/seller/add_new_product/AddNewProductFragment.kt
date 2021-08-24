package com.example.muzee.seller.add_new_product

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentAddNewProductBinding
import com.example.muzee.network.seller.product.ProductSeller
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class AddNewProductFragment : Fragment() {

    private var binding: FragmentAddNewProductBinding? = null // binding fragment_add_new_product.xml
    private val viewModel: AddNewProductFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                AddNewProductFragmentViewModel.ApiStatus.SUCCESS ->{
                    showDiaLog("Add product successful")
                }
                AddNewProductFragmentViewModel.ApiStatus.EXIST ->{
                    showDiaLog("This product has already existed")
                }
                AddNewProductFragmentViewModel.ApiStatus.ERROR ->{
                    showSnackBar()
                }
            }
        })
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
            val category = getIdOfCategory((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val stock = inputStock.editText?.text.toString().toInt()
            val description = binding?.labelNewProductDescription?.editText?.text.toString()
            val SID = AddNewProductFragmentArgs.fromBundle(requireArguments()).sellerID
            var sellerID = ""
            SID?.let{
                sellerID = it
            }
            val newProductSeller = ProductSeller(sellerID,null,category,description,null,name,price,stock)
            viewModel.addProduct(newProductSeller)
            findNavController().navigate(R.id.action_addNewProductFragment_to_sellerProductOverviewFragment)
        }
    }
    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,Category.Drum.name,Category.Electronic.name,Category.Guitar.name,Category.Piano.name,Category.Bass.name)
    }
    private fun showDiaLog(title:String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(title)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root,R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
    }
    private fun getIdOfCategory(category: String) = when(category){
            "Guitar Acoustic"->{
                 "002"
            }
            "Drum"->{
                 "003"
            }
            "Guitar Bass"->{
                 "005"
            }
            "Piano"->{
                 "001"
            }
            "Organ"->{
                "004"
            }
            else->{
                 "006"
            }
        }


}