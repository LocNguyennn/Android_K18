package com.example.android_w1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_w1.RestaurantStore

class MenuViewModel : ViewModel() {
    private var _listOfData : MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()
    val listOfData : LiveData<ArrayList<Restaurant>>
        get() = _listOfData
    fun loadData(){
        val data = RestaurantStore.getDataset()
        _listOfData.postValue(data)
    }
}