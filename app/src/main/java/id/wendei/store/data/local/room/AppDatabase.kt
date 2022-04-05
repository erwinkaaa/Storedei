package id.wendei.store.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.wendei.store.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDao
}