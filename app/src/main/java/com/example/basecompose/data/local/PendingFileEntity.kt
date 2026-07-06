package com.example.basecompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_files")
data class PendingFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val filePath: String,
    val status: String = "PENDING"
)