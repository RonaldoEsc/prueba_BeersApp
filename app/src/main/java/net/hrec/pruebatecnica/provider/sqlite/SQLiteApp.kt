package net.hrec.pruebatecnica.provider.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import net.hrec.pruebatecnica.model.BeerDataFavorite
import net.hrec.pruebatecnica.model.BeersResponse

class SQLiteApp(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val userTable = "CREATE TABLE $TABLE_USER_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_USER_NAME TEXT NOT NULL," +
                "$COLUMN_USER_PASSWORD TEXT NOT NULL)"

        val favoriteTable = "CREATE TABLE $TABLE_FAVORITE_NAME (" +
                "id INTEGER PRIMARY KEY," +
                "$COLUMN_BEER_ID INTEGER NOT NULL," +
                "$COLUMN_BEER_NAME TEXT NOT NULL," +
                "$COLUMN_BEER_TAG TEXT," +
                "$COLUMN_BEER_IMAGE TEXT," +
                "$COLUMN_BEER_RATE INTEGER NOT NULL)"
        db?.execSQL(userTable)
        db?.execSQL(favoriteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USER_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_NAME")
        onCreate(db)
    }

    private fun isBeerInTable(id: Int): String {
        val db = this.readableDatabase
        var name = ""
        val cursor =
            db.query(TABLE_FAVORITE_NAME, arrayOf(COLUMN_BEER_ID), null, null, null, null, null, null)
        if (db.isOpen) {
            if (cursor.count > 0) {
                do {
                    cursor.moveToFirst()
                    for (columnInfo in 0 until cursor.columnCount) {

                        if (id == cursor.getInt(cursor.getColumnIndex(COLUMN_BEER_ID))) {
                            name = cursor.getString(cursor.getColumnIndex(COLUMN_BEER_NAME))
                        }
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return name
    }

    fun insertOrUpdateFavoriteBeer(beer: BeerDataFavorite) {
        val values = ContentValues()
        values.put(COLUMN_BEER_ID, beer.id)
        values.put(COLUMN_BEER_NAME, beer.name)
        values.put(COLUMN_BEER_TAG, beer.tagline)
        values.put(COLUMN_BEER_IMAGE, beer.imageUrl)
        values.put(COLUMN_BEER_RATE, beer.rate)
        if (isBeerInTable(beer.id!!).isNotEmpty()) {
            val db = this.writableDatabase
            if (db.isOpen) {
                val row = db.update(TABLE_FAVORITE_NAME, values, "$COLUMN_BEER_ID=?", arrayOf(beer.id.toString()))
                Log.d("ROW DataBase", "$row")
                db.close()
            }
        } else {
            val db = this.writableDatabase
            if (db.isOpen) {
                val row = db.insert(TABLE_FAVORITE_NAME, null, values)
                Log.d("ROW DataBase", "$row")
                db.close()
            }
        }
    }

    fun deleteFavoriteBeer(id: Int) {
        val db = this.writableDatabase

        val where = "$COLUMN_BEER_ID=?"
        val whereArgs = arrayOf(id.toString())
        if (db.isOpen) {
            db.delete(TABLE_FAVORITE_NAME, where, whereArgs)
            db.close()
        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dbUserBeer"

        //tabla de usuarios
        const val TABLE_USER_NAME = "USERS"
        const val COLUMN_ID = "idInt"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_PASSWORD = "password"

        //tabla de favoritos
        const val TABLE_FAVORITE_NAME = "USERS"
        const val COLUMN_BEER_ID = "beerId"
        const val COLUMN_BEER_NAME = "beerName"
        const val COLUMN_BEER_TAG = "beerTag"
        const val COLUMN_BEER_IMAGE = "urlImage"
        const val COLUMN_BEER_RATE = "beerRate"
    }
}