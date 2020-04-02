package com.example.byrecipe.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.byrecipe.Model.User

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "ByRecipe.db"

        //Table
        private val TABLE_NAME="User"
        private val COL_EMAIL="Email"
        private val COL_PASSWORD="Password"
        private val COL_FULLNAME="Fullname"
        private val COL_NOPHONE="NoPhone"
        private val COL_ADDRESS="Address"
        private val COL_GENDER="Gender"
        private val COL_AGE="Age"
        private val COL_IMAGE="Image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String = ("CREATE TABLE $TABLE_NAME($COL_EMAIL TEXT PRIMARY KEY, $COL_PASSWORD TEXT, $COL_FULLNAME TEXT, $COL_NOPHONE TEXT, $COL_ADDRESS TEXT, $COL_GENDER TEXT, $COL_AGE INTEGER, $COL_IMAGE TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    //CRUD
    val allUser:List<User> get () {
        val listUsers = ArrayList<User>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val user = User()
                user.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                user.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                user.fullname = cursor.getString(cursor.getColumnIndex(COL_FULLNAME))
                user.noPhone = cursor.getString(cursor.getColumnIndex(COL_NOPHONE))
                user.address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                user.gender= cursor . getString (cursor.getColumnIndex(COL_GENDER))
                user.age = cursor.getInt(cursor.getColumnIndex(COL_AGE))
                user.image = cursor.getString(cursor.getColumnIndex(COL_IMAGE))

                listUsers.add(user)
            } while(cursor.moveToNext())
        }
        db.close()
        return listUsers
    }

    fun addUser(user: User){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, user.email)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_FULLNAME, user.fullname)
        values.put(COL_NOPHONE, user.noPhone)
        values.put(COL_ADDRESS, user.address)
        values.put(COL_GENDER, user.gender)
        values.put(COL_AGE, user.age)
        values.put(COL_IMAGE, user.image)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateUser(user: User):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, user.email)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_FULLNAME, user.fullname)
        values.put(COL_NOPHONE, user.noPhone)
        values.put(COL_ADDRESS, user.address)
        values.put(COL_GENDER, user.gender)
        values.put(COL_AGE, user.age)
        values.put(COL_IMAGE, user.image)

        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email.toString()))
    }

    fun deleteUser(user: User){
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_EMAIL=?", arrayOf(user.email.toString()))
        db.close()
    }

}