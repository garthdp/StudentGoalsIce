package com.garth.studentgoalsice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

lateinit var editor: Editor
lateinit var sharedPreferences: SharedPreferences

class settings : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode", "MissingInflatedId", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val swTheme : Switch = findViewById(R.id.swTheme)
        val swNotification : Switch = findViewById(R.id.swNotifications)
        val btnBackSettings : FloatingActionButton = findViewById(R.id.btnBackSettings)
        sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night", false)
        val notification = sharedPreferences.getBoolean("notification", false)

        if (notification){
            swNotification.isChecked = true
        }

        if(nightMode){
            swTheme.isChecked = true
        }

        btnBackSettings.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        swTheme.setOnCheckedChangeListener{ buttonView, isChecked ->
            editor.putBoolean("night", isChecked)
            editor.apply()
            if(!isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        swNotification.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(!isChecked){
                editor.putBoolean("notification", false)
                editor.apply()
            }
            else{
                editor.putBoolean("notification", true)
                editor.apply()
            }
        }
    }
}