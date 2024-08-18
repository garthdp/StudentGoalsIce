package com.garth.studentgoalsice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class editGoal : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_goal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cbCompleted : CheckBox = findViewById(R.id.cbCompleted)
        val txtEditTitle : TextView = findViewById(R.id.txtEditTitle)
        val txtEditDescription : TextView = findViewById(R.id.txtEditDescription)
        val btnEditGoal : Button = findViewById(R.id.btnEditGoal)

        cbCompleted.isChecked = currentGoal.completed

        btnEditGoal.setOnClickListener{
            if (txtEditTitle.text.isNotEmpty()){
                currentGoal.title = txtEditTitle.text.toString()
            }
            if (txtEditDescription.text.isNotEmpty()){
                currentGoal.description = txtEditDescription.text.toString()
            }
            if (cbCompleted.isChecked){
                currentGoal.completed = true
            }
            val db = DBHelper(this, null)
            val goalUpdated = db.updateGoal(currentGoal)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}