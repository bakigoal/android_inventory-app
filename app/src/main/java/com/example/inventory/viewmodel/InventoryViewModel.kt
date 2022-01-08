package com.example.inventory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.database.dao.ItemDao
import com.example.inventory.database.entity.Item
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getAll().asLiveData()

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getById(id).asLiveData()
    }

    fun addNewItem(name: String, price: String, count: String) {
        val newItem = Item(
            itemName = name,
            itemPrice = price.toDouble(),
            quantityInStock = count.toInt()
        )
        insertItem(newItem)
    }

    fun isEntryValid(name: String, price: String, count: String) =
        !(name.isBlank() || price.isBlank() || count.isBlank())

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}