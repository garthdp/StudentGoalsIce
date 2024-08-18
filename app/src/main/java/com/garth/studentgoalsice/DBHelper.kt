package com.garth.studentgoalsice

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory : SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    //below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        //below is a sqlite query, where column names
        // along with their data tupes is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT, " +
                DESCRIPTION_COL + " TEXT," +
                COMPLETED_COL + " BOOLEAN" + ")")

        // WE ARE CALLING SQLite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    //this method is for adding data in our database
    fun addGoal(title: String, description : String, completed: Boolean){

        //below we are creating
        // a content values variable
        val values = ContentValues()

        //we are inserting our values
        // in the form of key-value pair
        values.put(TITLE_COL, title)
        values.put(DESCRIPTION_COL, description)
        values.put(COMPLETED_COL, completed)

        // here we are creating a
        // writable variable of
        // our database as we want to
        //insert value in our database
        val db = this.writableDatabase

        // all values are inserted into databse
        db.insert(TABLE_NAME, null, values)

        //close database
        db.close()
    }

    //below method is to get
    // all data from our database
    fun getGoal(): Cursor? {

        // here we are creating a readable
        /// variable of our database
        // as we want to read value from t
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    //updates goal
    fun updateGoal(goal: Goal): Int {
        val db = this.readableDatabase
        val contentValues = ContentValues().apply {
            put(TITLE_COL, goal.title)
            put(DESCRIPTION_COL, goal.description)
            put(COMPLETED_COL, goal.completed)
        }

        // updating row based on the ID
        return db.update(
            TABLE_NAME,
            contentValues,
            "$ID_COL = ?",
            arrayOf(goal.id.toString())
        )
    }

    //deletes goal
    fun deleteGoal(goal: Goal): Int {
        val db = this.readableDatabase

        // updating row based on the ID
        return db.delete(
            TABLE_NAME,
            "$ID_COL = ?",
            arrayOf(goal.id.toString())
        )
    }

    companion object{
        // here we have defined variables for our database

        //below is variable for database name
        private val DATABASE_NAME = "SQL"

        //below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "tblGoals"

        //below is the variable for id column
        val ID_COL = "id"

        //below is the variable for id column
        val TITLE_COL = "title"

        // below is the variable for name column
        val DESCRIPTION_COL = "description"

        // below is the variable for age column
        val COMPLETED_COL = "completed"
    }
}