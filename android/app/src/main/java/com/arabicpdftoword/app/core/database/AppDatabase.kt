package com.arabicpdftoword.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arabicpdftoword.app.core.database.dao.ConversionDao
import com.arabicpdftoword.app.core.database.entity.ConversionEntity

@Database(entities = [ConversionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionDao(): ConversionDao
}
