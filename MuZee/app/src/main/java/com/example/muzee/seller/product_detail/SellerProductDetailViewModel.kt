package com.example.muzee.seller.product_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.data.Seller
import com.example.muzee.network.seller.product.ProductSeller

class SellerProductDetailViewModel(product: ProductSeller,seller: Seller, app: Application) : AndroidViewModel(app) {
    private val _selectedProduct = MutableLiveData<ProductSeller>()
    private val _sellerInfo = MutableLiveData<Seller>()

    // The extenal LiveData for the SelectedProduct
    val selectedProduct: LiveData<ProductSeller>
        get() = _selectedProduct
    val seller:LiveData<Seller>
        get() = _sellerInfo
    init {
        _selectedProduct.value = product
        _sellerInfo.value = seller
    }

    @SuppressLint("StringFormatInvalid")
    val displayPropertyPrice = Transformations.map(selectedProduct) {
        app.applicationContext.getString(R.string.display_price, it.productPrice)
    }
    fun getCategory() = when(_selectedProduct.value?.productCategory){
        "002"->{

            "Guitar Acoustic"
        }
        "003"->{

            "Drum"
        }
        "005"->{

            "Guitar Bass"
        }
        "001"->{

            "Piano"
        }
        "004"->{

            "Organ"
        }
        else->{
            "Electronic Guitar"
        }
    }
}