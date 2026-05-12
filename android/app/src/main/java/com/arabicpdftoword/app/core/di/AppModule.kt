package com.arabicpdftoword.app.core.di

import android.content.Context
import androidx.room.Room
import com.arabicpdftoword.app.core.common.Constants
import com.arabicpdftoword.app.core.database.AppDatabase
import com.arabicpdftoword.app.core.database.dao.ConversionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "arabic_pdf_to_word_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideConversionDao(database: AppDatabase): ConversionDao {
        return database.conversionDao()
    }

    @Provides
    @Singleton
    fun provideAppVersion(@ApplicationContext context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0.0"
        } catch (e: Exception) {
            "1.0.0"
        }
    }
}
