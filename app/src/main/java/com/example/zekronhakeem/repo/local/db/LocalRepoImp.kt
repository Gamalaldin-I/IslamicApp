package com.example.zekronhakeem.repo.local.db

import android.content.Context
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.LastRead
import com.example.zekronhakeem.model.Surah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepoImp(context: Context): LocalRepo {
    val db = DatabaseBuilder.getInstance(context)

    override suspend fun getAllSurah(): List<Surah> =
        withContext(Dispatchers.IO){
            db.surahDao().getAll()
        }

    override suspend fun insertSurah(surah: Surah) {
        withContext(Dispatchers.IO){
            db.surahDao().insertSurah(surah)
        }
    }

    override suspend fun deleteSurah() {
        withContext(Dispatchers.IO){
            db.surahDao().deleteAll()
        }
    }

    override suspend fun getSize(): Int {
        return db.surahDao().getCountSurah()
        }

    override suspend fun getSurahByNumber(number: Int): Surah =
        withContext(Dispatchers.IO){
            db.surahDao().getSurahByNumber(number)!!
        }

    override suspend fun getAyatBySurahNumber(number: Int): List<Ayah> =
        withContext(Dispatchers.IO){
        db.ayahDao().getVersesBySurahId(number)
     }

    override suspend fun getWholeAyat(): List<Ayah> =
        withContext(Dispatchers.IO){
            db.ayahDao().getAllAyat()
        }


    override suspend fun insertAyah(ayat: Ayah) {
         withContext(Dispatchers.IO){
             db.ayahDao().insertVerses(ayat)
         }
    }

    override suspend fun insertLastRead(lastRead: LastRead) {
        withContext(Dispatchers.IO){
            db.lastReadDao().insertLastRead(lastRead)
        }
    }

    override suspend fun getLastRead(): List<LastRead> =
         withContext(Dispatchers.IO){
            db.lastReadDao().getAllLastRead()
        }

    override suspend fun deleteLastRead(id: Int) {
        withContext(Dispatchers.IO){
            db.lastReadDao().deleteLastRead(id)
        }
    }

    override suspend fun deleteHistory() {
        withContext(Dispatchers.IO){
            db.lastReadDao().deleteAllLastRead()
        }
    }
}


