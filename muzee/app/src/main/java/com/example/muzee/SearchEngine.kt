package com.example.muzee

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData

class SearchEngine {
    private var keyword: String = ""
    private var categoryKey:String =""
    init {
        keyword = ""
        categoryKey = ""
    }
    private fun handleKey(){

    }
    fun sendKeytoBE(){
        handleKey()

    }
    fun setKeyWord(query: String){
        keyword = query
    }
    fun sendCategorytoBE(pickedCategory: String){
        categoryKey = pickedCategory
        // TO-DO: send request to BE
    }

}