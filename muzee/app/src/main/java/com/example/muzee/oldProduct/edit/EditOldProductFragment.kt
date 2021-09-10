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
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.muzee.R
import com.example.muzee.data.Category
import com.example.muzee.databinding.FragmentEditOldProductBinding
import com.example.muzee.network.AddOldProduct
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class EditOldProductFragment : Fragment() {
    private lateinit var binding:FragmentEditOldProductBinding
    private lateinit var viewModel:EditOldProductViewMoldel
    private val args: EditOldProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val application = requireNotNull(activity).application
        val fragmentbinding = FragmentEditOldProductBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        binding.lifecycleOwner = this
        val old_product = args.selectedOldProduct
        val viewModelFractory = EditOldProductViewModelFractory(old_product,application)
        viewModel = ViewModelProvider(this,viewModelFractory).get(EditOldProductViewMoldel::class.java)
        binding.viewModel = viewModel
        //handle list category
        val textField = binding.labelEditCategory
        val items = getListCategory()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        val index = getListCategory().indexOf(old_product.productCategory)
        (textField.editText as? AutoCompleteTextView)?.setText(getListCategory().get(index),false)

        //handle select image
        val plus_img = binding.editPlusImage
        plus_img.setOnClickListener({
            plus_img.isVisible = false
            dispatchTakePictureIntent()
        })
        handle_before_textchange()
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                EditOldProductViewMoldel.ApiStatus.SUCCESS->{
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                    dialog.setTitle("Edit product successful")
                        .setMessage("This product is edited and saved in system")
                        .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                            dialog.cancel()
                            findNavController().navigate(EditOldProductFragmentDirections.actionEditOldProductFragmentToOldProductStoreFragment(args.NID))
                        }
                    dialog.show()
                }
                EditOldProductViewMoldel.ApiStatus.ERROR->{
                    showSnackBar()
                }
                EditOldProductViewMoldel.ApiStatus.NOTEXIST->{
                    showDiaLog("Edit product failed","This product is not existed in system.")
                }
            }
        })

        return fragmentbinding.root
    }
    fun handle_confirm_btn()
    {
        val inputName = binding.labelEditName
        val selectCategory = binding.labelEditCategory
        val inputCondition = binding.labelEditCondition
        val inputDes = binding.labelEditProductDescription
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
            val categoryID = getCategoryID((selectCategory.editText as? AutoCompleteTextView)?.text.toString())
            val name = inputName.editText?.text.toString()

            val condition = inputCondition.editText?.text.toString().toInt()
            val description = inputDes.editText?.text.toString()
            var success = true
            if (condition < 0 || condition > 10) {
                success = false
                inputCondition.error = "Condition must be in 1-10"
            }
            if(selectCategory.editText!!.text!!.isEmpty()){
                success = false
                selectCategory.error = getString(R.string.error_text_CATEGORY)
            }
            if(inputName.editText!!.text!!.isEmpty()){
                success = false
                inputName.error = getString(R.string.error_text_PRODUCT_NAME)
            }
            if(success) {
                var normalUserId = ""
                args.NID?.let{
                    normalUserId = it
                }
                val oldProduct = AddOldProduct(
                    binding.viewModel!!.selectedOldProduct.value?.productId, categoryID, name
                    ,normalUserId,binding.viewModel!!.selectedOldProduct.value?.imageURI, description, condition)
                viewModel.editOldProduct(oldProduct)
            }


        }
    }
    private  fun handle_before_textchange(){
        binding.labelEditName.editText?.doBeforeTextChanged{_,_,_,_ ->
            binding.labelEditName.error = null
        }
        binding.labelEditCondition.editText?.doBeforeTextChanged{_,_,_,_ ->
            binding.labelEditCondition.error = null
        }
        binding.labelEditCategory.editText?.doBeforeTextChanged{_,_,_,_ ->
            binding.labelEditCategory.error = null
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@EditOldProductFragment
        }
    }

    private fun getListCategory():List<String>{
        return listOf(Category.Organ.name,
            Category.Drum.name,
            Category.Electric.name,
            Category.Guitar.name,
            Category.Piano.name,
            Category.Bass.name)
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
}