package com.arabicpdftoword.app.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arabicpdftoword.app.core.database.entity.ConversionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ConversionEntity): Long

    @Update
    suspend fun update(entity: ConversionEntity)

    @Delete
    suspend fun delete(entity: ConversionEntity)

    @Query("SELECT * FROM conversions ORDER BY created_at DESC")
    fun getAllConversions(): Flow<List<ConversionEntity>>

    @Query("SELECT * FROM conversions WHERE conversion_id = :conversionId LIMIT 1")
    suspend fun getByConversionId(conversionId: String): ConversionEntity?

    @Query("SELECT * FROM conversions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ConversionEntity?

    @Query("DELETE FROM conversions WHERE conversion_id = :conversionId")
    suspend fun deleteByConversionId(conversionId: String)

    @Query("DELETE FROM conversions")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM conversions")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM conversions WHERE created_at >= :sinceTimestamp")
    suspend fun getCountSince(sinceTimestamp: Long): Int

    @Query("UPDATE conversions SET status = :status, error_message = :errorMessage, completed_at = :completedAt WHERE conversion_id = :conversionId")
    suspend fun updateStatus(
        conversionId: String,
        status: String,
        errorMessage: String? = null,
        completedAt: Long? = null
    )

    @Query("UPDATE conversions SET output_file_name = :fileName, output_file_size = :fileSize, output_path = :filePath, status = 'completed', completed_at = :completedAt WHERE conversion_id = :conversionId")
    suspend fun markCompleted(
        conversionId: String,
        fileName: String?,
        fileSize: Long?,
        filePath: String?,
        completedAt: Long = System.currentTimeMillis()
    )
}
