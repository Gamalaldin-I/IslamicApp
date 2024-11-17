package com.example.zekronhakeem.repo.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zekronhakeem.model.Ayah

@Dao
interface AyahDao
{
    @Query("SELECT * FROM ayah WHERE surahId = :surahId ORDER BY number ASC " )
    suspend fun getVersesBySurahId(surahId:Int):List<Ayah>
    @Query("SELECT * FROM ayah")
    suspend fun getAllAyat():List<Ayah>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verse: Ayah)
}