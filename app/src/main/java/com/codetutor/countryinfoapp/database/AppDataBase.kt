package com.codetutor.countryinfoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codetutor.countryinfoapp.data.Country

@Database(entities = [Country::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase(){
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase (context: Context): AppDataBase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                INSTANCE
            }
        }
    }
}