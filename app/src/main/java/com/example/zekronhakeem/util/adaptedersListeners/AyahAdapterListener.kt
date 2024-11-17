package com.example.zekronhakeem.util.adaptedersListeners

import com.example.zekronhakeem.model.Ayah

interface AyahAdapterListener {
    fun getTafseer(ayah: Ayah, position: Int)
    fun share(ayah: Ayah, position: Int)
    fun saveLastRead(ayaNumber: Int)
    fun goToNextSura(surahNumber: Int)
}
