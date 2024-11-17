package com.example.zekronhakeem.util.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zekronhakeem.databinding.AyaItemSearchBinding
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.quranData.QuranInfo
import com.example.zekronhakeem.util.ArabicTranslator
import com.example.zekronhakeem.util.adaptedersListeners.AyatSearchListener

class SearchAyatAdapter(private var data: List<Ayah>,val listener: AyatSearchListener) :
    RecyclerView.Adapter<SearchAyatAdapter.JuzeHolder>() {

    // Create ViewHolder class
    class JuzeHolder(val binding:AyaItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuzeHolder {
        val binding = AyaItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JuzeHolder(binding)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: JuzeHolder, position: Int) {
        val data = data[position]
        holder.binding.suraName.text=QuranInfo.map[data.surahId]
        holder.binding.ayaContent.text=data.text
        holder.binding.ayaNumber.text=ArabicTranslator.toArabicNumerals(data.numberInSurah)
        holder.binding.root.setOnClickListener{
            listener.onClick(data)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: List<Ayah>) {
        data=filteredList
        notifyDataSetChanged()
    }

    // Return the size of the data list
    override fun getItemCount(): Int {
        return data.size
    }
}