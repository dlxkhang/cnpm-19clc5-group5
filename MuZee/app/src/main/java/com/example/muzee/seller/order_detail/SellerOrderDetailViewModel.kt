package com.example.muzee.seller.order_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.seller.order.AckResponse
import com.example.muzee.network.seller.order.FunctionOrderRequest
import com.example.muzee.network.seller.order.Order_responseItem
import kotlinx.coroutines.launch

class SellerOrderDetailViewModel(order: Order_responseItem, app: Application) : AndroidViewModel(app) {
    private val _selectedOrder = MutableLiveData<Order_responseItem>()
    enum class ApiStatus { ERROR,SUCCESS}
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    // The extenal LiveData for the SelectedProduct
    // The internal MutableLiveData that stores the status of the most recent request
    val selectedOrder: LiveData<Order_responseItem>
        get() = _selectedOrder
    private val _ack_accept = MutableLiveData<AckResponse>()
    val ack_accept:LiveData<AckResponse> = _ack_accept
    private val _ack_cancel = MutableLiveData<AckResponse>()
    val ack_cancel:LiveData<AckResponse> = _ack_cancel
    init {
        _selectedOrder.value = order
    }
    fun cancelOrder(SID:String){
        viewModelScope.launch {
            val input = FunctionOrderRequest(SID,_selectedOrder.value!!.orderId)
            try {
                val response = NetworkLayer.OrderApiClient.cancleOrder(input)
                if(response.isSuccessful){
                    _ack_cancel.value = response.body()
                    _status.value = ApiStatus.SUCCESS
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    fun acceptOrder(SID:String){
        viewModelScope.launch {
            val input = FunctionOrderRequest(SID,_selectedOrder.value!!.orderId)
            try {
                val response = NetworkLayer.OrderApiClient.acceptOrder(input)
                if(response.isSuccessful){
                    _ack_accept.value = response.body()
                    _status.value = ApiStatus.SUCCESS
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    @SuppressLint("StringFormatInvalid")
    val displayOrderDetails = Transformations.map(selectedOrder) {
        app.applicationContext.getString(R.string.order_label,it.orderId)
    }
}