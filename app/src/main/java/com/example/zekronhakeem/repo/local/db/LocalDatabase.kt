package com.example.zekronhakeem.repo.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.LastRead
import com.example.zekronhakeem.model.Surah
import com.example.zekronhakeem.repo.local.db.dao.AyahDao
import com.example.zekronhakeem.repo.local.db.dao.LastReadDao
import com.example.zekronhakeem.repo.local.db.dao.SurahDao


@Database(entities = [Surah::class, Ayah::class,LastRead::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun surahDao(): SurahDao
    abstract fun ayahDao(): AyahDao
    abstract fun lastReadDao(): LastReadDao
}