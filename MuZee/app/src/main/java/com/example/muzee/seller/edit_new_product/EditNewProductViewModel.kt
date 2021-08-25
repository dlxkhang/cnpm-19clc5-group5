package com.example.muzee.seller.edit_new_product

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.seller.product.ProductSeller
import kotlinx.coroutines.launch
import java.lang.Exception

class EditNewProductViewMoldel(newproduct: ProductSeller, app: Application) : AndroidViewModel(app) {
    private val _selectedNewProduct = MutableLiveData<ProductSeller>()
    enum class ApiStatus { NOTEXIST, ERROR, SUCCESS }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    // The extenal LiveData for the SelectedProduct
    val selectedNewProduct: LiveData<ProductSeller>
        get() = _selectedNewProduct

    init {
        _selectedNewProduct.value = newproduct
    }
    fun editProduct(product: ProductSeller){
        product.productId = _selectedNewProduct.value?.productId
        viewModelScope.launch {
            try {
                val response = NetworkLayer.SellerProductApiClient.editSellerProduct(product)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "product_not_exist"->{
                            _status.value = ApiStatus.NOTEXIST
                        }
                        "edit_product_success"->{
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    @SuppressLint("StringFormatInvalid")
    val displayProduct = Transformations.map(selectedNewProduct) {
        app.applicationContext.getString(R.string.new_product_label,it.productId)
    }
}