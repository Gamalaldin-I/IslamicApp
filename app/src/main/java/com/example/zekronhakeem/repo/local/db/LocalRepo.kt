package com.example.zekronhakeem.repo.local.db

import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.LastRead
import com.example.zekronhakeem.model.Surah


interface LocalRepo {
    //sura
    suspend fun getAllSurah(): List<Surah>
    suspend fun insertSurah(surah: Surah)
    suspend fun deleteSurah()
    suspend fun getSize():Int
    suspend fun getSurahByNumber(number:Int): Surah
    //ayat
    suspend fun getAyatBySurahNumber(number: Int):List<Ayah>
    suspend fun getWholeAyat():List<Ayah>
    suspend fun insertAyah(ayat: Ayah)
    //history(last read)
    suspend fun insertLastRead(lastRead:LastRead)
    suspend fun getLastRead():List<LastRead>
    suspend fun deleteLastRead(id: Int)
    suspend fun deleteHistory()


}