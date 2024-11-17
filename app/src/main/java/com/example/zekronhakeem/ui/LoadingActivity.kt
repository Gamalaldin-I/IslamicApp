package com.example.zekronhakeem.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.zekronhakeem.databinding.ActivityLoadingActivtyBinding
import com.example.zekronhakeem.jsonParsing.response.Aya
import com.example.zekronhakeem.jsonParsing.response.Quran
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.Surah
import com.example.zekronhakeem.repo.local.db.LocalRepoImp
import com.example.zekronhakeem.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class LoadingActivity : AppCompatActivity() {
    private var counter = 0.00f
    //database
    lateinit var db: LocalRepoImp
    lateinit var quran: Quran

    //binding
    private lateinit var binding: ActivityLoadingActivtyBinding
    //private lateinit var wifiStateReceiver: WifiStateReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        db = LocalRepoImp(this)
        quran=getAQuran()

        // Initialize the WifiStateReceiver
   //  val wifiStateReceiver = WifiStateReceiver(
   //      onWifiConnected = {
   //          // Resume fetching data when Wi-Fi is connected
   //          fetchDataFromApi()
   //      },
   //      onWifiDisconnected = {
   //          // Stop fetching data when Wi-Fi is disconnected
   //          stopFetchingData()
   //      }
   //  )
   //
   //  val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
   //  registerReceiver(wifiStateReceiver, intentFilter)

        binding = ActivityLoadingActivtyBinding.inflate(layoutInflater)
        runBlocking {
        loadingData(counter.toInt())}

        setContentView(binding.root)

    }

    private suspend fun loadingData(begin:Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            //try {

                //insert data to database
                for (i in begin..113) {
                    // loop on each sura in quran
                    val surah=quran.quran[i]
                    //prepare empty list of verses
                    // loop on each ayah in each sura in english and arabic
                    if (surah.id != 9 && surah.id != 1) {
                          surah.verses[0].arabicContent=removeBasmallahInArabic(surah.verses[0])
                    }
                    for (o in 0..<surah.versesNumber) {
                        val verse=surah.verses[o]
                        db.insertAyah(
                            Ayah(
                                verse.numberInQuran,
                                surah.id,
                                verse.numberInSura,
                                false,
                                false,
                                verse.arabicContent,
                                removeTashkeel(verse.arabicContent),
                                verse.englishContent,
                                verse.tafser
                            )
                        )
                    }

                    //insert sura to database
                    db.insertSurah(
                        Surah(
                            surah.id,
                            surah.name,
                            surah.englishName,
                            surah.type,
                            surah.versesNumber
                        )
                    )
                    counter++

                    withContext(Dispatchers.Main) {
                        updateUi()
                    }
                }
            withContext(Dispatchers.Main) {
            startActivity( Intent(this@LoadingActivity, MainActivity::class.java))}

          //} catch (e: Exception) {
          //    withContext(Dispatchers.Main) {
          //        Log.d(e.message, "loadingData: ${e.message}")
          //    }

            //}
        }
        }


    private fun updateUi() {
        binding.ncom.text = counter.toInt().toString()
        binding.progressBar.progress= ((counter / 114.00) * 100).toInt()
    }
    //remove basmallah from the arabic text
    private fun removeBasmallahInArabic(ayah: Aya): String {
        return ayah.arabicContent.substring(39)
    }



private fun getAQuran(): Quran {
    val inputStream=assets.open("wholeQuran.json")
    val inputStreamReader= InputStreamReader(inputStream)
    val quran= Gson().fromJson(inputStreamReader, Quran::class.java)
    inputStreamReader.close()
    inputStream.close()
    return quran
}





override fun onDestroy() {
    super.onDestroy()
    // Unregister the receiver when the activity is destroyed
    //unregisterReceiver(wifiStateReceiver)
}

private fun fetchDataFromApi() {
    binding.noInternetMessage.visibility=GONE
    runBlocking {
        // Fetch data from the API and update the UI
        //the benefit of using counter as a parameter is to continue the loading process from the last point
        loadingData(counter.toInt())
    }
}

private fun stopFetchingData() {
    // Add logic to stop the data fetching process
    binding.noInternetMessage.visibility=VISIBLE
    // This might involve cancelling a Retrofit/Volley request
}
    fun removeTashkeel(text: String): String {
        return text.replace(Regex("[\\u0610-\\u061A\\u064B-\\u0652]"), "").replace("ٰ","ا").replace("ٱ","ا").replace("ٓ","").replace("ۡ","")
    }
}






