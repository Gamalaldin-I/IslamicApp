package com.example.zekronhakeem.ui.main.quran

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import com.example.zekronhakeem.R
import com.example.zekronhakeem.ui.surah.SurahActivity
import com.example.zekronhakeem.databinding.FragmentQuranBinding
import com.example.zekronhakeem.model.Ayah
import com.example.zekronhakeem.repo.local.db.LocalRepoImp
import com.example.zekronhakeem.model.Surah
import com.example.zekronhakeem.repo.local.sheardPrefrence.ShardPreference
import com.example.zekronhakeem.ui.history.HistoryActivity
import com.example.zekronhakeem.util.adaptedersListeners.AyatSearchListener
import com.example.zekronhakeem.util.adaptedersListeners.FehresAdapterListener
import com.example.zekronhakeem.util.adapters.SearchAyatAdapter
import com.example.zekronhakeem.util.adapters.SuraFehresAdapter

class QuranFragment : Fragment(), FehresAdapterListener ,AyatSearchListener{
private lateinit var binding: FragmentQuranBinding
lateinit var db: LocalRepoImp
private lateinit var pref: ShardPreference
private lateinit var surahList:List<Surah>
private lateinit var ayatList:List<Ayah>
private lateinit var adapter:SuraFehresAdapter
private lateinit var suraSearchAdapter:SuraFehresAdapter
private lateinit var ayahSearchAdapter:SearchAyatAdapter
private lateinit var viewModel: QuranViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment


        binding = FragmentQuranBinding.inflate(inflater, container, false)
        binding.surahRadioBtn.isChecked=true
        suraSearchAdapter= SuraFehresAdapter(listOf(),this)
        ayahSearchAdapter= SearchAyatAdapter(listOf(),this)
        db= LocalRepoImp(requireContext())
        pref= ShardPreference(requireContext())
        surahList= listOf()

        viewModel= ViewModelProvider(this@QuranFragment)[QuranViewModel()::class.java]

        //set the quran list to the recycler view
        viewModel.quranList.observe(viewLifecycleOwner) {
            surahList = it
            adapter = SuraFehresAdapter(surahList, this)
            binding.fehresRecyclerView.adapter = adapter
        }
        viewModel.ayahList.observe(viewLifecycleOwner){
            ayatList=it
        }
        viewModel.getWholeAyas(db)
        viewModel.getQuranList(db)

        //open the last read sura card
        binding.lastReadInfoCard.setOnClickListener{
           openLastReadSurah()
        }

        //search for surah
        setupSearchViewListeners()
        binding.searchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    binding.resultRecyclerView.visibility=GONE
                    binding.resultsCount.visibility= GONE
                }
                else{
                controlSearch(binding.options.checkedRadioButtonId,newText.toString())
                binding.options.setOnCheckedChangeListener { group, checkedId ->
                    binding.resultsCount.visibility= GONE
                    for (i in 0 until group.childCount) {
                        val radioButton = group.getChildAt(i) as RadioButton
                        if (radioButton.id == checkedId) {
                            radioButton.animate().scaleX(1.1f).scaleY(1.1f).alpha(1f).duration = 300
                        } else {
                            radioButton.animate().scaleX(1f).scaleY(1f).alpha(0.7f).duration = 300
                        }
                    }
                                 controlSearch(checkedId,newText.toString())}}
                return true}

        })
        binding.historyBtn.setOnClickListener{
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }




        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun filterAyat(text:String){
        val filteredList=viewModel.filterAyaSearch(text,ayatList)


        if(filteredList.isNotEmpty()){
            binding.resultRecyclerView.visibility=VISIBLE
            ayahSearchAdapter.setFilteredList(filteredList)
            binding.resultRecyclerView.adapter=ayahSearchAdapter
            binding.resultsCount.text=" النتائج  ${filteredList.size}"
            binding.resultsCount.visibility= VISIBLE
        }
        else{
            binding.resultRecyclerView.visibility=GONE
            Toast.makeText(requireContext(), "لا توجد نتائج مطابقة", Toast.LENGTH_SHORT).show()
            binding.resultsCount.visibility= GONE
        }


    }
    @SuppressLint("SetTextI18n")
    private fun filterSuras(text:String){
        val filteredList=viewModel.filterSuraSearch(text,surahList)


        if(filteredList.isNotEmpty()){
            binding.resultRecyclerView.visibility=VISIBLE
            suraSearchAdapter.setFilteredList(filteredList)
            binding.resultsCount.text=" النتائج  ${filteredList.size}"
            binding.resultsCount.visibility= VISIBLE
            binding.resultRecyclerView.adapter=suraSearchAdapter
        }
        else{
            binding.resultRecyclerView.visibility=GONE
            Toast.makeText(requireContext(), "لا توجد نتائج مطابقة", Toast.LENGTH_SHORT).show()
            binding.resultsCount.visibility= GONE
        }


    }






    //open the surah activity when a surah is clicked
    override fun surahOnClicked(surah: Surah, position: Int) {
        val intent= Intent(requireContext(), SurahActivity::class.java)
        intent.putExtra("surahNumber",surah.surahNumber)
        startActivity(intent)
    }
    // set last read surah info to the intent to open it
    private fun openLastReadSurah(){
        val intent3=Intent(requireContext(), SurahActivity::class.java)
        viewModel.setLastSuraInfo(pref,intent3)
        startActivity(intent3)
    }
    //set the last read surah info to the text view
    override fun onResume(){
        super.onResume()
        val (lstSuraName,ayaNumber)=viewModel.getLastSuraInfo(pref)
        binding.lastSurahNameTV.text=lstSuraName
        binding.lastAyahNumTV.text=if(ayaNumber==0) "لم تحدد " else ayaNumber.toString()
    }


    private fun setupSearchViewListeners() {
        // Move SearchView to the top and hide CardView when search is opened
        binding.searchView.setOnSearchClickListener {
            moveSearchViewToTopAndHideCard()
        }

        // Return SearchView to original position and show CardView when search is closed
        binding.searchView.setOnCloseListener {
            restoreSearchViewAndShowCard()
            false // Needed to indicate we handled the close event
        }
    }

    private fun moveSearchViewToTopAndHideCard() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)

        // Move the SearchView to the top of the layout
        constraintSet.clear(R.id.searchView, ConstraintSet.TOP)
        constraintSet.connect(R.id.searchView, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        binding.searchControlView.visibility=VISIBLE
        binding.fehresRecyclerView.visibility=GONE

        // Hide the CardView
        binding.lastReadInfoCard.visibility =GONE

        // Apply changes with animation
        TransitionManager.beginDelayedTransition(binding.constraintLayout)
    }

    private fun restoreSearchViewAndShowCard() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)

        // Move the SearchView back under the CardView
        constraintSet.clear(R.id.searchView, ConstraintSet.TOP)
        constraintSet.connect(R.id.searchView, ConstraintSet.TOP, R.id.lastReadInfoCard, ConstraintSet.BOTTOM)
        binding.searchControlView.visibility=GONE
        binding.fehresRecyclerView.visibility=VISIBLE

        // Show the CardView again
        binding.lastReadInfoCard.visibility = VISIBLE

        // Apply changes with animation
        TransitionManager.beginDelayedTransition(binding.constraintLayout)
        //constraintSet.applyTo(binding.constraintLayout)
    }
    fun controlSearch(radioButtonId:Int,text: String){
        when(radioButtonId){
            R.id.surahRadioBtn->{
                filterSuras(text)
            }
            R.id.ayahRadioBtn->{
                filterAyat(text)
            }
    }}

    override fun onClick(aya: Ayah) {
        val intent= Intent(requireContext(), SurahActivity::class.java)
        intent.putExtra("position",aya.numberInSurah)
        intent.putExtra("surahNumber",aya.surahId)
        startActivity(intent)

    }
}
