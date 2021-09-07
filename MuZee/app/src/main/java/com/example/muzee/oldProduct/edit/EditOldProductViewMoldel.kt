package com.example.muzee.oldProduct.edit

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.AddOldProduct
import com.example.muzee.network.OldProduct
import kotlinx.coroutines.launch

class EditOldProductViewMoldel(oldproduct: OldProduct, app: Application) : AndroidViewModel(app) {
    private val _selectedOldProduct = MutableLiveData<OldProduct>()

    // The extenal LiveData for the SelectedProduct
    val selectedOldProduct: LiveData<OldProduct>
        get() = _selectedOldProduct
    enum class ApiStatus { NOTEXIST, ERROR, SUCCESS }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    init {
        _selectedOldProduct.value = oldproduct
    }
    fun editOldProduct(old_product: AddOldProduct){
        viewModelScope.launch {
            try{
                val response = Api.retrofitService.editOldProduct(old_product)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "old_product_not_exist"->{
                            _status.value = ApiStatus.NOTEXIST
                        }
                        "edit_old_product_success"->{
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    @SuppressLint("StringFormatInvalid")
    val displayOldProduct = Transformations.map(selectedOldProduct) {
        app.applicationContext.getString(R.string.old_product_label,it.productId)
    }
}