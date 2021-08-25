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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentEditNewProductBinding
import com.example.muzee.network.seller.product.ProductSeller
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class EditNewProductFragment : Fragment() {
    private lateinit var binding:FragmentEditNewProductBinding
    private lateinit var viewModel:EditNewProductViewMoldel
    private val args:EditNewProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentEditNewProductBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding.lifecycleOwner = this
        val new_product = args.selectedNewProduct
        val viewModelFractory = EditNewProductViewModelFractory(new_product,application)
        viewModel = ViewModelProvider(this,viewModelFractory).get(EditNewProductViewMoldel::class.java)
        //handle list category
        binding.viewModel = viewModel
        val textField = binding.labelEditCategory
        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        val index = getListCategory().indexOf(viewModel.selectedNewProduct.value?.productCategory.toString())
        (textField.editText as? AutoCompleteTextView)?.setText(getListCategory().get(index),false)
        //handle select image
        val plus_img = binding.editPlusImage
        plus_img.setOnClickListener({
            plus_img.isVisible = false
            dispatchTakePictureIntent()
        })
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                EditNewProductViewMoldel.ApiStatus.SUCCESS->{
                    showDiaLog("Edit product successful", "This product is edited and saved in system")
                    findNavController().navigate(EditNewProductFragmentDirections.actionEditNewProductFragmentToSellerProductOverviewFragment(args.sellerID,args.sellerInfo))
                }
                EditNewProductViewMoldel.ApiStatus.ERROR->{
                    showSnackBar()
                }
                EditNewProductViewMoldel.ApiStatus.NOTEXIST->{
                    showDiaLog("Edit product failed","This product is not existed in system.")
                }
            }
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
            val category = getIdOfCategory((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()
            val price = inputPrice.editText?.text.toString().toDouble()
            val stock = inputStock.editText?.text.toString().toInt()
            val description = binding.labelEditProductDescription.editText?.text.toString()
            val SID = args.sellerID
            val editProduct = ProductSeller(SID!!,null,category,description,null,name,price,stock)
            viewModel.editProduct(editProduct)
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
    private fun showDiaLog(title:String,message:String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }
    private fun showSnackBar(){
        Snackbar.make(binding.root,R.string.connection_error_title, Snackbar.LENGTH_SHORT).show()
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