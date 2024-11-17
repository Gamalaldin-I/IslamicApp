package com.example.zekronhakeem.ui.surah

import Dialogs
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zekronhakeem.databinding.ActivitySurahBinding
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.Surah
import com.example.zekronhakeem.repo.local.db.LocalRepoImp
import com.example.zekronhakeem.repo.local.sheardPrefrence.ShardPreference
import com.example.zekronhakeem.util.ArabicTranslator
import com.example.zekronhakeem.util.adaptedersListeners.AyahAdapterListener
import com.example.zekronhakeem.util.adapters.SuraAyatAdapter
import kotlin.properties.Delegates

class SurahActivity : AppCompatActivity() , AyahAdapterListener {
    private var nextSurahNumber=0
    private var translate=false
    private lateinit var ayat: List<Ayah>
    private lateinit var pref: ShardPreference
    private lateinit var ayatAdapter: SuraAyatAdapter
    private lateinit var nextSurah: Surah
    lateinit var db: LocalRepoImp
    lateinit var binding: ActivitySurahBinding
    private var surahNumber by Delegates.notNull<Int>()
    private lateinit var sura: Surah
    lateinit var surahViewModel:SurahViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //storage
        ayat= listOf()
//sura= Surah(0,"11","","",100)
     //   nextSurah= Surah(0,"","","",0)
        db= LocalRepoImp(this)
        pref= ShardPreference(this)

        //inflate view
        binding=ActivitySurahBinding.inflate(layoutInflater)
        //get data from intent
        val surahNumber=intent.getIntExtra("surahNumber",1)
        val positionY=intent.getIntExtra("position",-1)
        if(surahNumber in 81..114||surahNumber==1) {binding.seekbar.visibility=GONE}
        //ayatAdapter=SuraAyatAdapter(listOf(),this,positionY)
        //binding.fehresRecyclerView.adapter=ayatAdapter
        //get data from view model
        ayatAdapter= SuraAyatAdapter(ayat,this,positionY)
        binding.fehresRecyclerView.adapter=ayatAdapter
        surahViewModel= ViewModelProvider(this)[SurahViewModel::class.java]

        surahViewModel.ayahs.observe(this){
            ayat=it
            ayatAdapter.ayatPrepared(ayat)
            scroll(positionY)
            binding.seekbar.max = ayat.size - 1

        }
        surahViewModel.surah.observe(this){
            sura=it
            viewSurahInfo(sura.arabicName)
            ayatAdapter.setSuraInfo(sura)
            this@SurahActivity.surahNumber =sura.surahNumber

            if(sura.surahNumber<114) {
                surahViewModel.getNextSurah(db,sura.surahNumber)
                //if (sura.surahNumber == 114)
                //the adapter will remove the view of the next surah automatic
            }
            surahViewModel.resetPref(pref,sura.arabicName,sura.surahNumber)

        }
        surahViewModel.nextSurah.observe(this){
            nextSurah=it
            ayatAdapter.setNextSuraInfo(nextSurah)
            nextSurahNumber=it.surahNumber
        }
        //get from view Model
        surahViewModel.getSurahByNumber(db,surahNumber)
        surahViewModel.getAyahsBySurahNumber(db,surahNumber)

        //controllers
        binding.backArrow.setOnClickListener{
            finish()
        }






        // Listener for SeekBar changes to control scrolling
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // Scroll the RecyclerView to the item at the SeekBar position
                    (binding.fehresRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(progress, 0)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.fehresRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                binding.seekbar.progress = firstVisibleItemPosition
                if (lastVisibleItemPosition==binding.fehresRecyclerView.adapter?.itemCount?.minus(1)) {
                    binding.seekbar.animate().setStartDelay(0).scaleX(20f).withEndAction {
                        binding.seekbar.progress = binding.seekbar.max

                    }.setDuration(500).start()
            }
                else{
                    binding.seekbar.animate().scaleX(1f).setDuration(0).start()
                }
            }
        })


        ayatAdapter= SuraAyatAdapter(ayat,this,positionY)
        binding.fehresRecyclerView.adapter=ayatAdapter

        binding.translationBtn.setOnClickListener{
            binding.translationBtn.animate().rotationBy(180f).setDuration(250).start()
            translate=!translate
            ayatAdapter.translate(translate)
        }

        setContentView(binding.root)

    }
    //set surah View info
    private fun viewSurahInfo(suraName:String){
        binding.suraName.text=suraName
     }


    override fun getTafseer(ayah: Ayah, position: Int) {
        Dialogs.showTafserDialog(this,ayah.tafser,sura.arabicName,ayah.numberInSurah)
    }

    override fun share(ayah: Ayah, position: Int) {
        val sharedText= "${ayah.text}  ${sura.arabicName} (${ArabicTranslator.toArabicNumerals(ayah.numberInSurah)})"
        val shareIntent=Intent(Intent.ACTION_SEND)
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,sharedText)
        startActivity(Intent.createChooser(shareIntent,"Share via"))
    }

     override fun saveLastRead(ayaNumber: Int) {
         surahViewModel.setPref(pref,sura.arabicName,sura.surahNumber,ayaNumber)
    }



    override fun goToNextSura(surahNumber: Int) {
        val intent2=Intent(this, SurahActivity::class.java)
        intent2.putExtra("surahNumber",surahNumber)
        startActivity(intent2)
        this.finish()}

     private fun scroll(i:Int){
         if(i<0) return
         binding.fehresRecyclerView.post {
             // Delay to ensure layout is rendered
                     binding.fehresRecyclerView.scrollToPosition(i)
         }
     }

    override fun onStop() {
        super.onStop()
        val ayat=ayatAdapter.savedAyas
        if(ayat.isNotEmpty()){
            surahViewModel.addToHistory(db,sura,ayat)
        }
    }
    }





