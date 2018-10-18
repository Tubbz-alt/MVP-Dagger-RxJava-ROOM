package mvp.cooking.com.foodlist.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import mvp.cooking.com.foodlist.data.model.Food

@Database(entities = [Food::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "food.db"
        const val VERSION = 1
    }
    abstract fun foodDao(): FoodDao
}