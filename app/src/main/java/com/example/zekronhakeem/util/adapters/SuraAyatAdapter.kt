package com.example.zekronhakeem.util.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zekronhakeem.R
import com.example.zekronhakeem.databinding.AyahCardBinding
import com.example.zekronhakeem.databinding.SuraFooterBinding
import com.example.zekronhakeem.databinding.SuraHeaderBinding
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.model.Surah
import com.example.zekronhakeem.util.ArabicTranslator
import com.example.zekronhakeem.util.adaptedersListeners.AyahAdapterListener

class SuraAyatAdapter(private var data: List<Ayah>, val listener:AyahAdapterListener, val highLightAyaPosition:Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
         var sura=Surah(0,"","","",0)
         var nextSura=Surah(0,"","","",0)
    private var translationOptio=false
    private var selectedAyahIndex:Int=-1
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_FOOTER = 2
     val savedAyas=ArrayList<Int>()

    fun translate(option:Boolean){
        translationOptio=option
        notifyItemRangeChanged(1,itemCount-2)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER // Header at the top
            itemCount - 1 -> VIEW_TYPE_FOOTER // Footer at the bottom
            else -> VIEW_TYPE_ITEM // Other positions are for items
        }
    }
    fun setSuraInfo(sura:Surah){
        this.sura=sura
        notifyItemChanged(0)
    }
    fun setNextSuraInfo(sura:Surah){
        this.nextSura=sura
        notifyItemChanged(itemCount-1)
    }
    fun ayatPrepared(ayat:List<Ayah>){
        data=ayat
        notifyItemRangeChanged(1,itemCount-2)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding =
                    SuraHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_FOOTER -> {
                val binding =
                    SuraFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                 FooterViewHolder(binding)
            }

            else -> {
                val binding =
                    AyahCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AyaHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }

            is AyaHolder -> {
                // Calculate the position within the items list (adjust for header)
                val itemPosition = position - 1
                val item = data[itemPosition]
                holder.bind(item,itemPosition,holder)
            }

            is FooterViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + 2 // +2 for header and footer
    }


    inner class HeaderViewHolder(val binding: SuraHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            if(sura.surahNumber==9) binding.bsmallahImage.visibility=GONE
            binding.arabicName.text = sura.arabicName
            binding.englishName.text = sura.englishName
            binding.englishName.visibility= VISIBLE
            binding.numberOfVerses.text = ArabicTranslator.toArabicNumerals(sura.numberOfVerses)
            binding.revelationType.text=ArabicTranslator.meccanOrMedinan(sura.revelationType)
        }
    }

    inner class FooterViewHolder(val binding: SuraFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){
                if(sura.surahNumber==114) binding.root.visibility=GONE
                else{
                binding.nextArabicName.text=nextSura.arabicName
                binding.nextEnglishName.text=nextSura.englishName
                binding.nextNumberOfVerses.text=ArabicTranslator.toArabicNumerals(nextSura.numberOfVerses)
                binding.root.setOnClickListener{
                    listener.goToNextSura(nextSura.surahNumber)
                }
                }}
            }

    inner class AyaHolder(val binding: AyahCardBinding) : RecyclerView.ViewHolder(binding.root) {
           @SuppressLint("ResourceAsColor")
           fun bind(ayah: Ayah, position: Int, holder: AyaHolder) {
               //when happen notyfy the adapter to update the view of the previos selected ayah0
               //AdapterActivation\\
               if(!ayah.selected) deselectAyah(ayah,holder)
               if(ayah.selected) selectAyah(ayah,holder)
               onSaveView(ayah,holder)
               /////////////////////////////bind the data to the view \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
               binding.arabicAyahText.text = ayah.text
               binding.englishAyahText.text = ayah.translationText
               binding.verseNumber.text = ArabicTranslator.toArabicNumerals(ayah.numberInSurah)
             if(translationOptio){
                 binding.englishAyahText.visibility=VISIBLE
             }
             else{
                 binding.englishAyahText.visibility=GONE
             }

               //to handle the long click on ayah to select it
               binding.arabicAyahText.setOnLongClickListener {
                   if(selectedAyahIndex==-1){   //if the user select the first ayah
                       selectAyah(ayah,holder)
                       selectedAyahIndex=position
                   }
                   else if(selectedAyahIndex!=position){
                       //if the user select another ayah
                       //deselect the previous one
                       modifyThePreviousSelectedAyah()
                       //select the new one
                       selectAyah(ayah,holder)
                       selectedAyahIndex=position
                   }
                   true
               }

               //to handle the click on ayah to deselect it
               binding.arabicAyahText.setOnClickListener {
                   if(ayah.selected){
                       selectedAyahIndex=-1
                       deselectAyah(ayah,holder)}
               }


               binding.tafseerBtn.setOnClickListener{
                   animateBtn(binding.tafseerBtn)
                   listener.getTafseer(ayah,position)
               }
               binding.ayahNumContainer.setOnClickListener{
                   animateBtn(binding.saveBtn)
                   animateBtn(binding.ayahNumContainer)
                   if(ayah.saved){
                       removeAyah(ayah)
                   }
                   else {
                       saveAyah(ayah)
                   }
                   onSaveView(ayah,holder)
                   setLastRead()
               }

               binding.saveBtn.setOnClickListener{
                   animateBtn(binding.saveBtn)
                   animateBtn(binding.ayahNumContainer)
                   if(ayah.saved){
                     removeAyah(ayah)
                   }
                   else {
                       saveAyah(ayah)
                   }
                   onSaveView(ayah,holder)
                   setLastRead()
               }

               binding.shareBtn.setOnClickListener{
                   animateBtn(binding.shareBtn)
                   listener.share(ayah,position)

               }
               if (ayah.numberInSurah == highLightAyaPosition) {
                   // Set initial background to selected_ayah_bg
                   binding.arabicAyahText.setBackgroundResource(R.drawable.selected_ayah_bg)

                   // Delay to reset background after 2 seconds
                   Handler(Looper.getMainLooper()).postDelayed({
                       binding.arabicAyahText.animate().alpha(0f).setDuration(700).withEndAction {
                           binding.arabicAyahText.setBackgroundResource(R.color.transparent)
                           binding.arabicAyahText.animate().alpha(1f).setDuration(400).start()
                       }.start()
                   }, 1000)
               }

           }

    }
    @SuppressLint("ResourceAsColor")
    fun onSaveView(ayah: Ayah, holder: AyaHolder){
        if(ayah.saved){
            holder.binding.saveBtn.setImageResource(R.drawable.saved_icon)
            holder.binding.ayahNumContainer.setImageResource(R.drawable.saved_icon)
            holder.binding.verseNumber.setTextColor(Color.parseColor("#EDE5D7"))
        }
        else {
            holder.binding.saveBtn.setImageResource(R.drawable.save_icon)
            holder.binding.ayahNumContainer.setImageResource(R.drawable.islamic_star_ayah)
            holder.binding.verseNumber.setTextColor(Color.parseColor("#2B8154"))
        }
    }
    private fun selectAyah(ayah: Ayah, holder: AyaHolder){
        ayah.selected=true
        holder.binding.arabicAyahText.setBackgroundResource(R.drawable.selected_ayah_bg)
        holder.binding.toolsCard.visibility=VISIBLE
    }
    private fun deselectAyah(ayah: Ayah, holder: AyaHolder){
        ayah.selected=false
        holder.binding.arabicAyahText.setBackgroundResource(R.color.transparent)
        holder.binding.toolsCard.visibility=GONE
    }
    private fun modifyThePreviousSelectedAyah(){
        //modify the previous selected ayah
        //if the user select another ayah
        //deselect the previous one
        data[selectedAyahIndex].selected=false //i wrote it here for when the user select another ayah
        //notify the adapter to update the view of the previos selected ayah
        //the adapter will see if it not selected HE WILL  update with deselectAyah
        notifyItemChanged(selectedAyahIndex+1) //i  added +1 because the adapter start from 1 fetching data from the list
        // and the header takes one position 0 in adapter
        //so if i want to deselect the previous ayah i its position in dataList is i and in adapter is i+1
    }
    fun animateBtn(btn: View){
        btn.animate().alpha(1f).scaleX(1.2f).scaleY(1.2f).
        setDuration(200).withEndAction { btn.animate().alpha(1f).scaleX(0.4f).scaleY(0.4f) }.setDuration(100).start()
        Handler(Looper.getMainLooper()).postDelayed({
            btn.animate().alpha(1f).scaleX(1f).scaleY(1f).
            setDuration(400).start()
        },300)
    }
    fun saveAyah(ayah: Ayah){
        ayah.saved=true
        savedAyas.add(ayah.numberInSurah)
    }
    fun removeAyah(ayah: Ayah){
        ayah.saved=false
        savedAyas.remove(ayah.numberInSurah)
    }
    fun  setLastRead(){
        if(savedAyas.size>0)
        {
            listener.saveLastRead(savedAyas[savedAyas.size-1])// save the last read ayah that in the savedAyas list
        }
    }



}
