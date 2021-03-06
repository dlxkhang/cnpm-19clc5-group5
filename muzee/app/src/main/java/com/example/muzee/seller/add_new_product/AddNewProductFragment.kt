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
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentAddNewProductBinding
import com.example.muzee.network.seller.product.ProductSeller
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class AddNewProductFragment : Fragment() {

    private var binding: FragmentAddNewProductBinding? = null // binding fragment_add_new_product.xml
    private val viewModel: AddNewProductFragmentViewModel by viewModels()
    private val args:AddNewProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setup data binding
        val fragmentBinding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding?.lifecycleOwner = this
        handle_before_textchange()
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
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("Add product successful")
                        .setMessage("A new product is added to system")
                        .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                            dialog.cancel()
                            findNavController().navigate(AddNewProductFragmentDirections.actionAddNewProductFragmentToSellerProductOverviewFragment(args.sellerID,args.sellerInfo))
                        }
                    dialog.show()

                }
                AddNewProductFragmentViewModel.ApiStatus.EXIST ->{
                    showDiaLog("Add product failed", "This product has been existed in system")
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
    private fun handle_before_textchange(){
        binding?.labelInputName?.editText?.doBeforeTextChanged{ _,_,_,_ ->
            binding?.labelInputName?.error = null
        }
        binding?.labelInputPrice?.editText?.doBeforeTextChanged{ _,_,_,_ ->
            binding?.labelInputPrice?.error = null
        }
        binding?.labelInputStockNewProduct?.editText?.doBeforeTextChanged{ _,_,_,_ ->
            binding?.labelInputStockNewProduct?.error = null
        }
        binding?.labelNewProductDescription?.editText?.doBeforeTextChanged{ _,_,_,_ ->
            binding?.labelNewProductDescription?.error = null
        }
        binding?.labelSelectCategory?.editText?.doBeforeTextChanged{ _,_,_,_ ->
            binding?.labelSelectCategory?.error = null
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
        if(inputStock!!.editText!!.text!!.isNotEmpty() ){
            if(inputStock!!.editText!!.text!!.toString().toInt()<=0) {
                success = false
                inputStock.error = "Cannot enter non-negative product's stock"
            }
        }
        if(success){
            val category = getCategoryID((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val stock = inputStock.editText?.text.toString().toInt()
            val description = binding?.labelNewProductDescription?.editText?.text.toString()
            val SID = args.sellerID
            var sellerID = ""
            SID?.let{
                sellerID = it
            }
            val newProductSeller = ProductSeller(sellerID,null,category,description,null,name,price,stock)
            viewModel.addProduct(newProductSeller)

        }
    }
    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,Category.Drum.name,Category.Electric.name,Category.Guitar.name,Category.Piano.name,Category.Bass.name)
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

}