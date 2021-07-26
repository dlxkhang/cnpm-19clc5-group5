package com.example.muzee

import androidx.lifecycle.MutableLiveData

class SearchEngine {
    private var keyword: String = ""
    private var categoryKey:String =""
    fun handleKey(){

    }
    fun sendKeytoBE(){

    }
    fun sendCategorytoBE(pickedCategory: String){
        categoryKey = pickedCategory
        // TO-DO: send request to BE
    }

}