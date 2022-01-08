package com.example.inventory

import android.app.Application
import com.example.inventory.database.InventoryDB

class InventoryApplication : Application() {

    val database: InventoryDB by lazy { InventoryDB.getDatabase(this) }

}
