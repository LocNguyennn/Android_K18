package com.example.android_w1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_w1.model.Restaurant

class MenuViewModel : ViewModel() {

    private var _listOfData : MutableLiveData<List<Restaurant>> = MutableLiveData()
    val listOfData : LiveData<List<Restaurant>>
        get() = _listOfData
    fun loadData(){
        val data = RestaurantStore.getDataset()
        _listOfData.postValue(data)
    }
}