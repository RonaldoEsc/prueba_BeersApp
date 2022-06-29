package net.hrec.pruebatecnica.provider.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteApp(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val userTable = "CREATE TABLE $TABLE_USER_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT NOT NULL," +
                "$COLUMN_PASSWORD TEXT NOT NULL)"
        val favoriteTable = ""
        db?.execSQL(userTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USER_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dbUserBeer"

        //tabla de usuarios
        const val TABLE_USER_NAME = "USERS"
        const val COLUMN_ID = "idInt"
        const val COLUMN_NAME = "name"
        const val COLUMN_PASSWORD = "idInt"
    }
}