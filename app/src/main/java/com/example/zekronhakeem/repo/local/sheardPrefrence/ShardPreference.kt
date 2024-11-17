package com.example.zekronhakeem.repo.local.sheardPrefrence

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class ShardPreference (context: Context){
    private val lastSurahPreference: SharedPreferences =context.getSharedPreferences("surahPref",Context.MODE_PRIVATE)

    val editor=lastSurahPreference.edit()
    @SuppressLint("CommitPrefEdits")
    fun setSurahName(surahName:String,num:Int){
        editor.putString("surahName",surahName)
        editor.putInt("surahNumber",num)
        editor.apply()
    }
    @SuppressLint("CommitPrefEdits")
    fun setAyahNumber(ayahNumber:String){
        editor.putString("ayahNumber",ayahNumber)
        editor.apply()
    }


    fun getSurahNumber():Int{
        return lastSurahPreference.getInt("surahNumber",0)
    }

    fun getSruahName():String{
       return lastSurahPreference.getString("surahName","لم تحدد")!!
    }
    fun getAyahNumber():Int{
         val lastAya=lastSurahPreference.getString("ayahNumber","لم تحدد")!!
        if(lastAya=="لم تحدد"){
            return 0
        }
        return lastAya.toInt()
    }
}