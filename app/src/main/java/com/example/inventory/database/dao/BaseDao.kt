package com.example.inventory.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(t: T)

    @Update
    suspend fun update(t: T)

    @Delete
    suspend fun delete(vararg t: T)

}