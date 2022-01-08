package com.example.inventory.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.inventory.database.entity.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao: BaseDao<Item> {

    /**
     * Using Flow or LiveData as return type will ensure you get notified whenever the data in the DB changes.
     * It is recommended to use Flow in the persistence layer.
     * The Room keeps this Flow updated for you, which means you only need to explicitly get the data once.
     * This is helpful to update the inventory list, which you will implement in the next codelab.
     * Because of the Flow return type, Room also runs the query on the background thread.
     * You don't need to explicitly make it a suspend function and call inside a coroutine scope.
     */
    @Query("SELECT * FROM items WHERE id=:id")
    fun getById(id: Int): Flow<Item>

    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAll(): Flow<List<Item>>
}