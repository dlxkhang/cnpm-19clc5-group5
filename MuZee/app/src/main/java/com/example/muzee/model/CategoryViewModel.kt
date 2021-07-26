package com.example.muzee.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzee.SearchEngine

class CategoryViewModel: ViewModel() {
    private val _searchEngine = MutableLiveData<SearchEngine?>()
    init{
        resetSearchEngine()
    }
    fun resetSearchEngine(){
        _searchEngine.value = SearchEngine()
    }
    fun updateCategoryKey(pickedCategory: String) {
        _searchEngine.value?.sendCategorytoBE(pickedCategory)
    }
}
