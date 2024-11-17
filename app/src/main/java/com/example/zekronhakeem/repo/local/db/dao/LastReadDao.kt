package com.example.zekronhakeem.repo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.zekronhakeem.model.LastRead

@Dao
interface LastReadDao {
    @Query("SELECT * FROM last_read ORDER BY id DESC")
    suspend fun getAllLastRead(): List<LastRead>
    @Insert
    suspend fun insertLastRead(lastRead: LastRead)
    @Query("DELETE FROM last_read WHERE id = :id")
    suspend fun deleteLastRead(id: Int)
    @Query("DELETE FROM last_read")
    suspend fun deleteAllLastRead()



}