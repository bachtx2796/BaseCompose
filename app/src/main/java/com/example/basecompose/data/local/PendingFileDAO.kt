package com.example.basecompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PendingFileDao {

    @Insert
    suspend fun insert(file: PendingFileEntity)

    @Query("SELECT * FROM pending_files WHERE status = 'PENDING'")
    suspend fun getPending(): List<PendingFileEntity>

    @Query("DELETE FROM pending_files WHERE id = :id")
    suspend fun delete(id: Long)
}