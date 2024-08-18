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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
        val btnAddGoals: Button = findViewById(R.id.btnCreateGoal)
        val addGoalBack: FloatingActionButton = findViewById(R.id.addGoalBack)
        txtTitle = findViewById(R.id.txtDisplayTitle)
        txtDescription = findViewById(R.id.txtDisplayDescription)

        btnAddGoals.setOnClickListener {
            val goal = Goal(0, txtTitle.text.toString(), txtDescription.text.toString(), false)
            val db = DBHelper(this, null)
            // calling method to add
            // name and age to our database
            db.addGoal(goal.title, goal.description, goal.completed)

            // Toast to message on the screen
            Toast.makeText(this, "$title added to database", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        addGoalBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}