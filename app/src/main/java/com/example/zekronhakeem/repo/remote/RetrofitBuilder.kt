package com.example.zekronhakeem.repo.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    //urls
    private const val   QURAN_URL = "https://api.alquran.cloud/v1/quran/"
    private const val   QURAN_TAFSER="https://quranenc.com/api/v1/translation/sura/"
    //retrofits
    private var quranRetrofit: Retrofit? = null
    private var quranTafseerRetrofit: Retrofit? = null
    //converter
    val gson: Gson = GsonBuilder()
        .create()
     const val QURAN=1
     const val TAFSER=2
    //functions
    private fun getRetrofit(retrofit:Int,url:String): Retrofit {
        var retrofit=when(retrofit){
            QURAN -> quranRetrofit
            TAFSER -> quranTafseerRetrofit
            else->null
        }
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }

    //interfaces



}