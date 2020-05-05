package com.example.byrecipe.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.byrecipe.Model.User

class DBHelperUser(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object{
        internal val DATABASE_VER = 1
        val DATABASE_NAME = "ByRecipe.dbUser"

        //Table
        private val TABLE_NAME="User"
        private val COL_EMAIL="Email"
        private val COL_PASSWORD="Password"
        private val COL_FULLNAME="Fullname"
        private val COL_NOPHONE="NoPhone"
        private val COL_ADDRESS="Address"
        private val COL_GENDER="Gender"
        private val COL_AGE="Age"
        private val COL_CODE="Code"
        private val COL_IMAGE="Image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String = ("CREATE TABLE $TABLE_NAME($COL_EMAIL TEXT PRIMARY KEY, $COL_PASSWORD TEXT, $COL_FULLNAME TEXT, $COL_NOPHONE TEXT, $COL_ADDRESS TEXT, $COL_GENDER TEXT, $COL_AGE INTEGER, $COL_CODE TEXT, $COL_IMAGE BLOB)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    val allUser:List<User> get () {
        val listUsers = ArrayList<User>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val user = User("", "", "", "", "", "", 0, "")
                user.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                user.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                user.fullname = cursor.getString(cursor.getColumnIndex(COL_FULLNAME))
                user.noPhone = cursor.getString(cursor.getColumnIndex(COL_NOPHONE))
                user.address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                user.gender= cursor.getString (cursor.getColumnIndex(COL_GENDER))
                user.age = cursor.getInt(cursor.getColumnIndex(COL_AGE))
                user.code = cursor.getString(cursor.getColumnIndex(COL_CODE))

                listUsers.add(user)
            } while(cursor.moveToNext())
        }
        db.close()
        return listUsers
    }

    fun addUserSetFirst(user: User): User{
        val selectQuery = "SELECT $COL_EMAIL FROM $TABLE_NAME WHERE $COL_EMAIL='"+user.email+"'"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()==false) {
            val values = ContentValues()
            values.put(COL_EMAIL, user.email)
            values.put(COL_PASSWORD, user.password)
            values.put(COL_FULLNAME, user.fullname)
            values.put(COL_NOPHONE, user.noPhone)
            values.put(COL_ADDRESS, user.address)
            values.put(COL_GENDER, user.gender)
            values.put(COL_AGE, user.age)
            values.put(COL_CODE, user.code)
            values.putNull(COL_IMAGE)

            db.insert(TABLE_NAME, null, values)
            return user
        } else {
            var selectQuery2 = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL='"+user.email+"'"
            val cursor2: Cursor = db.rawQuery(selectQuery2, null)
            if(cursor2.moveToFirst()){
                user.email = cursor2.getString(cursor2.getColumnIndex(COL_EMAIL))
                user.password = cursor2.getString(cursor2.getColumnIndex(COL_PASSWORD))
                user.fullname = cursor2.getString(cursor2.getColumnIndex(COL_FULLNAME))
                user.noPhone = cursor2.getString(cursor2.getColumnIndex(COL_NOPHONE))
                user.address = cursor2.getString(cursor2.getColumnIndex(COL_ADDRESS))
                user.gender= cursor2.getString (cursor2.getColumnIndex(COL_GENDER))
                user.age = cursor2.getInt(cursor2.getColumnIndex(COL_AGE))
                user.code = cursor2.getString(cursor2.getColumnIndex(COL_CODE))
                return user
            }
        }
        db.close()
        return user
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
        values.put(COL_CODE, user.code)
        values.putNull(COL_IMAGE)

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

        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email.toString()))
    }

    fun updatePasswordUser(email: String, newPassword: String): Int{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_PASSWORD, newPassword)

        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(email))
    }

    fun updateImageProfile(user: User,  dataImage: ByteArray): Int{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_IMAGE, dataImage)

        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email.toString()))
    }

    fun getImageProfile(user: User): Bitmap? {
        var bmImage: Bitmap?
        val selectQuery = "SELECT $COL_IMAGE FROM $TABLE_NAME WHERE $COL_EMAIL='" + user.email.toString() + "'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            if(cursor.getBlob(0)!=null){
                var imgByte: ByteArray = cursor.getBlob(0)
                bmImage = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.size)
                cursor.close()
                return bmImage
            } else {
                return null
            }
        } else {
            cursor.close()
        }
        return null
    }

    fun deleteUser(user: User){
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_EMAIL=?", arrayOf(user.email.toString()))
        db.close()
    }

}