package com.example.zekronhakeem.util.adaptedersListeners

import com.example.zekronhakeem.model.Surah

interface FehresAdapterListener {
    fun surahOnClicked(surah: Surah, position: Int)
}