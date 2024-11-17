package com.example.zekronhakeem.repo.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zekronhakeem.model.Surah


@Dao
interface SurahDao {
    @Query("SELECT * FROM surah")
    suspend fun getAll(): List<Surah>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurah(surah: Surah)

    @Query("SELECT * FROM surah WHERE surahNumber = :number")
    suspend fun getSurahByNumber(number: Int): Surah?

    @Query("DELETE FROM surah")
    suspend fun deleteAll()
    @Query("SELECT COUNT(*) FROM surah")
    suspend fun getCountSurah(): Int


}