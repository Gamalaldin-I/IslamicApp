package com.example.zekronhakeem.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.zekronhakeem.R
import com.example.zekronhakeem.databinding.ActivityMainBinding
import com.example.zekronhakeem.ui.main.quran.QuranFragment

class MainActivity : AppCompatActivity() {
    private lateinit var nightModePref: SharedPreferences
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(QuranFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.qibla -> {
                    replaceFragment(QiplaFragment())
                    true
                }
                R.id.quranBtn -> {
                    replaceFragment(QuranFragment())
                    true
                }
                R.id.salawatBtn -> {
                    replaceFragment(SalawatFragment())
                    true
                }
                else -> false
        }

    }
        /*nightModePref=getSharedPreferences("setting", MODE_PRIVATE)
        //get if is in night mode or not
        val isNightMode = nightModePref.getBoolean("night_mode", false)
        //set the switch state based on the night mode
        //binding.nightModeSwitch.isChecked = isNightMode
        //set the night mode based on the switch state
        setNightMode(isNightMode)
        //set the listener for the switch
        binding.nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setNightMode(isChecked)
            saveNightModePreference(isChecked)
        }*/

}
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
    private fun setNightMode(enabled: Boolean) {
        val mode = if (enabled) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
    private fun saveNightModePreference(enabled: Boolean) {
        nightModePref.edit().putBoolean("night_mode", enabled).apply()
    }
}


