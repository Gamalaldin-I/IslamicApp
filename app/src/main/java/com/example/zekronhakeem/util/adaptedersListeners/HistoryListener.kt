package com.example.zekronhakeem.util.adaptedersListeners

import com.example.zekronhakeem.model.LastRead

interface HistoryListener {
    fun onClick(last:LastRead)
    fun onDelete(last:LastRead,position:Int)
}
