package com.example.inventory.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventory.database.dao.ItemDao
import com.example.inventory.database.entity.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var INSTANCE: InventoryDB? = null

        fun getDatabase(context: Context): InventoryDB {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, InventoryDB::class.java, "inventory-db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}