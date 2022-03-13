package com.example.basicroomdb

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicroomdb.database.DataDao
import com.example.basicroomdb.database.DataModel
import kotlinx.coroutines.*

class FirstViewModel(private val dataSource: DataDao, application: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var allname : LiveData<List<DataModel>>? = dataSource.getAllData()

    private var _name = MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name

    fun onSave(text : String){
        _name.value = text
        onSaveButton()
    }
    private fun onSaveButton () {
        uiScope.launch{
            var newName = name
            Log.v("bala3", newName.value.toString())
            insert(newName.value.toString())
        }
    }
    private suspend fun insert(newName: String) {
        withContext(Dispatchers.IO){
            dataSource.insert(DataModel(0,newName))
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
            allname = null
        }
    }
    private suspend fun clear(){
        withContext(Dispatchers.IO){
            dataSource.deleteAllData()
        }
    }

}