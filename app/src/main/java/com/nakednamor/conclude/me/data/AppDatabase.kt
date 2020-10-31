package com.nakednamor.conclude.me.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nakednamor.conclude.me.DATABASE_NAME
import com.nakednamor.conclude.me.data.weight.WeightDao
import com.nakednamor.conclude.me.data.weight.WeightRecord

@Database(entities = [WeightRecord::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weightDao(): WeightDao

    companion object {
        // singleton pattern
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
        }
    }
}