package com.garth.studentgoalsice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.icu.text.Transliterator.Position
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.database.getBlobOrNull

val goals = ArrayList<Goal>()
lateinit var currentGoal : Goal

class MainActivity : AppCompatActivity() {
    private lateinit var recyleview: RecyclerView
    private lateinit var btnAdd: FloatingActionButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyleview = findViewById(R.id.rcGoals)
        btnAdd = findViewById(R.id.btnAddGoal)
        var id : Int
        var title : String
        var description : String
        var completed : Boolean
        val btnSettings : FloatingActionButton = findViewById(R.id.btnSettings)
        val goalAdapter = GoalAdapter()

        recyleview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=goalAdapter
        }

        Handler(Looper.getMainLooper()).post{
            val db = DBHelper(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getGoal()

            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            if (goals.isEmpty()){
                id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)).toInt()
                title = cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL))
                description = cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION_COL))
                completed = cursor.getString(cursor.getColumnIndex(DBHelper.COMPLETED_COL)).toBoolean()
                Log.d("Goal Info", title + description + completed)
                var goal = Goal(id, title, description, completed)
                goals.add(goal)

                // moving our cursor to next
                // position and appending values
                while(cursor.moveToNext()){
                    id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)).toInt()
                    title = cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL))
                    description = cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION_COL))
                    completed = cursor.getString(cursor.getColumnIndex(DBHelper.COMPLETED_COL)).toBoolean()
                    Log.d("Goal Info", title + description + completed)
                    goal = Goal(id, title, description, completed)
                    goals.add(goal)
                }
                Log.d("Goals", goals.toString())
            }

            // at last we close our cursor
            cursor.close()

            goalAdapter.submitList(goals)
        }

        btnSettings.setOnClickListener{
            val intent = Intent(this, settings::class.java)
            startActivity(intent)
        }

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddGoals::class.java)
            startActivity(intent)
        }

        goalAdapter.setOnClickListener(object : GoalAdapter.OnClickListener{
            override fun onClick(position: Int, model: Goal) {
                currentGoal = model
                val intent = Intent(this@MainActivity, editGoal::class.java)
                startActivity(intent)
            }
        })
    }
    override fun onResume(){
        super.onResume()
        sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night", false)

        if(nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}