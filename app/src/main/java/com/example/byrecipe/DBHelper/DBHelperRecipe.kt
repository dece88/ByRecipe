package com.example.byrecipe.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.byrecipe.Model.Recipe

class DBHelperRecipe(context: Context): SQLiteOpenHelper(context, DBHelperUser.DATABASE_NAME, null, DBHelperUser.DATABASE_VER) {

    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "ByRecipe.dbRecipe"

        //Table
        private val TABLE_NAME="Recipe"
        private val COL_NAMA="Nama"
        private val COL_INGREDIENTS = "Ingredients"
        private val COL_TAHAPAN="Tahapan"
        private val COL_WAKTU="Waktu"
        private val COL_OWNER="Owner"
        private val COL_CATEGORY="Category"
        private val COL_IMAGE="Image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY: String = ("CREATE TABLE $TABLE_NAME($COL_NAMA TEXT, $COL_INGREDIENTS TEXT,$COL_TAHAPAN TEXT, $COL_WAKTU TEXT, $COL_OWNER TEXT, $COL_CATEGORY TEXT, $COL_IMAGE BLOB)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    val allRecipe:List<Recipe> get () {
        val listRecipes = ArrayList<Recipe>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val recipe = Recipe("", "", "", "", "", "")
                recipe.nama = cursor.getString(cursor.getColumnIndex(COL_NAMA))
                recipe.ingredients = cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS))
                recipe.tahapan = cursor.getString(cursor.getColumnIndex(COL_TAHAPAN))
                recipe.waktu = cursor.getString(cursor.getColumnIndex(COL_WAKTU))
                recipe.owner = cursor.getString(cursor.getColumnIndex(COL_OWNER))
                recipe.category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY))

                listRecipes.add(recipe)
            } while(cursor.moveToNext())
        }
        db.close()
        return listRecipes
    }

//    fun addUserSetFirst(user: User): User {
//        val selectQuery = "SELECT $COL_EMAIL FROM $TABLE_NAME WHERE $COL_EMAIL='"+user.email+"'"
//        val db = this.writableDatabase
//        val cursor: Cursor = db.rawQuery(selectQuery, null)
//        if (cursor.moveToFirst()==false) {
//            val values = ContentValues()
//            values.put(COL_EMAIL, user.email)
//            values.put(COL_PASSWORD, user.password)
//            values.put(COL_FULLNAME, user.fullname)
//            values.put(COL_NOPHONE, user.noPhone)
//            values.put(COL_ADDRESS, user.address)
//            values.put(COL_GENDER, user.gender)
//            values.put(COL_AGE, user.age)
//            values.put(COL_CODE, user.code)
//            values.putNull(COL_IMAGE)
//
//            db.insert(TABLE_NAME, null, values)
//            return user
//        } else {
//            var selectQuery2 = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL='"+user.email+"'"
//            val cursor2: Cursor = db.rawQuery(selectQuery2, null)
//            if(cursor2.moveToFirst()){
//                user.email = cursor2.getString(cursor2.getColumnIndex(COL_EMAIL))
//                user.password = cursor2.getString(cursor2.getColumnIndex(COL_PASSWORD))
//                user.fullname = cursor2.getString(cursor2.getColumnIndex(COL_FULLNAME))
//                user.noPhone = cursor2.getString(cursor2.getColumnIndex(COL_NOPHONE))
//                user.address = cursor2.getString(cursor2.getColumnIndex(COL_ADDRESS))
//                user.gender= cursor2.getString (cursor2.getColumnIndex(COL_GENDER))
//                user.age = cursor2.getInt(cursor2.getColumnIndex(COL_AGE))
//                user.code = cursor2.getString(cursor2.getColumnIndex(COL_CODE))
//                return user
//            }
//        }
//        db.close()
//        return user
//    }
//
    fun addUser(recipe: Recipe){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAMA, recipe.nama)
        values.put(COL_INGREDIENTS, recipe.ingredients)
        values.put(COL_TAHAPAN, recipe.tahapan)
        values.put(COL_WAKTU, recipe.waktu)
        values.put(COL_OWNER, recipe.waktu)
        values.putNull(COL_IMAGE)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
//
//    fun updateUser(user: User):Int{
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_EMAIL, user.email)
//        values.put(COL_PASSWORD, user.password)
//        values.put(COL_FULLNAME, user.fullname)
//        values.put(COL_NOPHONE, user.noPhone)
//        values.put(COL_ADDRESS, user.address)
//        values.put(COL_GENDER, user.gender)
//        values.put(COL_AGE, user.age)
//
//        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email.toString()))
//    }
//
//    fun updatePasswordUser(email: String, newPassword: String): Int{
//        val db = this.writableDatabase
//        val values = ContentValues()
//
//        values.put(COL_PASSWORD, newPassword)
//
//        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(email))
//    }
//
//    fun updateImageProfile(user: User, dataImage: ByteArray): Int{
//        val db = this.writableDatabase
//        val values = ContentValues()
//
//        values.put(COL_IMAGE, dataImage)
//
//        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email.toString()))
//    }
//
//    fun getImageProfile(user: User): Bitmap? {
//        var bmImage: Bitmap?
//        val selectQuery = "SELECT $COL_IMAGE FROM $TABLE_NAME WHERE $COL_EMAIL='" + user.email.toString() + "'"
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(selectQuery, null)
//
//        if (cursor.moveToFirst()){
//            if(cursor.getBlob(0)!=null){
//                var imgByte: ByteArray = cursor.getBlob(0)
//                bmImage = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.size)
//                cursor.close()
//                return bmImage
//            } else {
//                return null
//            }
//        } else {
//            cursor.close()
//        }
//        return null
//    }
//
//    fun deleteUser(user: User){
//        val db = this.writableDatabase
//
//        db.delete(TABLE_NAME,"$COL_EMAIL=?", arrayOf(user.email.toString()))
//        db.close()
//    }
}