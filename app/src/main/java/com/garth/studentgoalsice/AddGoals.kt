package com.garth.studentgoalsice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import androidx.activity.enableEdgeToEdge


class AddGoals : AppCompatActivity() {
    private lateinit var txtTitle: TextView
    private lateinit var txtDescription: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_goals)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnAddGoals: Button = findViewById<Button>(R.id.btnCreateGoal)
        txtTitle = findViewById(R.id.txtDisplayTitle)
        txtDescription = findViewById(R.id.txtDisplayDescription)

        btnAddGoals.setOnClickListener {
            val goal = Goal(txtTitle.text.toString(), txtDescription.text.toString(), false)
            goals.add(goal)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}