package com.example.zekronhakeem.ui.history

import Dialogs
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.zekronhakeem.databinding.ActivityHistoryBinding
import com.example.zekronhakeem.model.LastRead
import com.example.zekronhakeem.repo.local.db.LocalRepoImp
import com.example.zekronhakeem.ui.surah.SurahActivity
import com.example.zekronhakeem.util.HistoryAdapter
import com.example.zekronhakeem.util.adaptedersListeners.HistoryListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity(),HistoryListener{
    private lateinit var binding: ActivityHistoryBinding
    lateinit var db:LocalRepoImp
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter:HistoryAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        db= LocalRepoImp(this)
        viewModel= ViewModelProvider(this)[HistoryViewModel::class.java]

        adapter= HistoryAdapter(mutableListOf(),this)
        binding.historyRecyclerView.adapter=adapter
        getData()

        binding.clearBtn.setOnClickListener{
        if(adapter.data.isNotEmpty()){
            handleClear()
        }
            else{
            Toast.makeText(this, "السجل فارغ", Toast.LENGTH_SHORT).show()
        }
    }
        setContentView(binding.root)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        viewModel.clear(db)
        viewModel.historyList.observe(this){
            adapter.updateData(it as MutableList<LastRead>)
            adapter.notifyDataSetChanged()
            if(adapter.itemCount==0){
                binding.hint.visibility= VISIBLE
            }
        }

    }

    override fun onClick(last: LastRead) {
        val intent = Intent(this, SurahActivity()::class.java)
        intent.putExtra("surahNumber",last.suraNumber)
        intent.putExtra("position",last.ayahNumber)
        startActivity(intent)
        finish()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        var data:List<LastRead>
        viewModel.getHistoryList(db)
        viewModel.historyList.observe(this){
            data=it as MutableList<LastRead>
            adapter.updateData(data)
            adapter.notifyDataSetChanged()
            if(adapter.itemCount==0){
                binding.hint.visibility= VISIBLE
        }

    }
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onDelete(last: LastRead, position: Int) {
        var data:MutableList<LastRead>
        GlobalScope.launch(Dispatchers.IO) {
            db.deleteLastRead(last.id)
            data=db.getLastRead().toMutableList()
            withContext(Dispatchers.Main){
                adapter.updateData(data)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position,adapter.itemCount)
                if(adapter.itemCount==0){
                    binding.hint.visibility= VISIBLE
                }
            }
        }


        }
    private fun handleClear(){
        Dialogs.showAlertDialog(this," هل تريد محو السجل بالكامل ؟","تحذير",
            "نعم",
            "تراجع",
            {
                clear()
            },
            {
            })
    }
}