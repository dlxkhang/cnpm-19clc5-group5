package com.example.muzee.order_detail

import Api
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.CancelOrder
import com.example.muzee.network.UserOrder
import kotlinx.coroutines.launch

class OrderDetailViewModel(val order: UserOrder, val NID: String?, app: Application) : AndroidViewModel(app) {
    val _selectedOrder = MutableLiveData<UserOrder>()

    // The extenal LiveData for the SelectedProduct
    val selectedOrder: LiveData<UserOrder>
        get() = _selectedOrder

    @SuppressLint("StringFormatInvalid")
    val displayOrderDetails = Transformations.map(selectedOrder) {
        app.applicationContext.getString(R.string.order_label,it.orderId)
    }

    fun cancelOrder(){
        viewModelScope.launch {
            try {
                val cancelOrder = CancelOrder(NID, selectedOrder.value?.orderId)
                val temp = Api.retrofitService.cancelOrder(cancelOrder)
            } catch (e: Exception) {

            }
        }
    }
}