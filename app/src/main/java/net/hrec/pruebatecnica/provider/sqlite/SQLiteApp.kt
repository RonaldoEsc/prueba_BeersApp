package net.hrec.pruebatecnica.provider.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.model.LoginUserData

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
    /** * * * * * * * *
     * Tabla usuarios *
     * * * * * * * * * */

    fun insertUser(user: LoginUserData) {
        val values = ContentValues()
        values.put(COLUMN_BEER_NAME, user.userName)
        values.put(COLUMN_BEER_TAG, user.Password)
        val db = this.writableDatabase
        if (db.isOpen) {
            db.insert(TABLE_FAVORITE_NAME, null, values)
            db.close()
        }
    }

    fun getUser(): List<LoginUserData> {
        val db = this.readableDatabase
        val list = mutableListOf<LoginUserData>()

        if (db.isOpen) {
            val columns = arrayOf(COLUMN_USER_NAME)

            val cursor = db.query(
                TABLE_USER_NAME, columns, null, null, null, null,
                null, null
            )
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val objRequest = LoginUserData(
                        userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                    )
                    list.add(objRequest)
                } while (cursor.moveToNext())
            }
        }
        return list.toList()
    }

    fun deleteUser(user: String) {
        val db = this.writableDatabase

        val where = "$COLUMN_USER_NAME=?"
        val whereArgs = arrayOf(user)
        if (db.isOpen) {
            db.delete(TABLE_FAVORITE_NAME, where, whereArgs)
            db.close()
        }
    }

    /** * * * * * * * **
     * tabla favoritos *
     * * * * * * * * * */
    private fun isBeerInTable(id: Int): String {
        val db = this.readableDatabase
        var name = ""
        if (db.isOpen) {
            val cursor =
                db.query(TABLE_FAVORITE_NAME, arrayOf(COLUMN_BEER_ID), null, null, null, null, null, null)
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
            db.close()
        }
        return name
    }

    fun getAllFavorites(): List<BeersResponse> {
        val db = this.readableDatabase
        val list = mutableListOf<BeersResponse>()
        if (db.isOpen) {
            val columns = arrayOf(
                COLUMN_BEER_ID, COLUMN_BEER_NAME, COLUMN_BEER_TAG,
                COLUMN_BEER_IMAGE, COLUMN_BEER_RATE
            )

            val cursor = db.query(
                TABLE_FAVORITE_NAME, columns, null, null, null, null,
                "$COLUMN_BEER_RATE DESC", null
            )
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val objRequest = BeersResponse()
                    objRequest.id = cursor.getInt(cursor.getColumnIndex(COLUMN_BEER_ID))
                    objRequest.name = cursor.getString(cursor.getColumnIndex(COLUMN_BEER_NAME))
                    objRequest.tagline = cursor.getString(cursor.getColumnIndex(COLUMN_BEER_TAG))
                    objRequest.imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_BEER_IMAGE))
                    objRequest.rate = cursor.getInt(cursor.getColumnIndex(COLUMN_BEER_RATE))
                    list.add(objRequest)
                } while (cursor.moveToNext())
            }
        }
        return list.toList()
    }

    fun insertFavoriteBeer(beer: BeersResponse) {
        val values = ContentValues()
        values.put(COLUMN_BEER_ID, beer.id)
        values.put(COLUMN_BEER_NAME, beer.name)
        values.put(COLUMN_BEER_TAG, beer.tagline)
        values.put(COLUMN_BEER_IMAGE, beer.imageUrl)
        values.put(COLUMN_BEER_RATE, beer.rate)
        val db = this.writableDatabase
        if (db.isOpen) {
            db.insert(TABLE_FAVORITE_NAME, null, values)
            db.close()
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

    fun isFavoriteBeer(id: Int): Boolean {
        val db = this.readableDatabase
        if (db.isOpen) {
            val cursor = db.query(
                TABLE_FAVORITE_NAME, arrayOf(COLUMN_BEER_ID), null, null, null, null,
                COLUMN_BEER_ID, null
            )
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val tableId = cursor.getInt(cursor.getColumnIndex(COLUMN_BEER_ID))
                    if (tableId == id) {
                        db.close()
                        return true
                    }
                } while (cursor.moveToNext())
            }
            db.close()
            return false
        }
        return false
    }

    fun updateRate(id: Int, rate: Int) {
        val values = ContentValues()
        values.put(COLUMN_BEER_RATE, rate)
        val db = this.writableDatabase
        if(db.isOpen) {
            db.update(TABLE_FAVORITE_NAME, values, "$COLUMN_BEER_ID=?", arrayOf(id.toString()))
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
        const val TABLE_FAVORITE_NAME = "FAVORITE"
        const val COLUMN_BEER_ID = "beerId"
        const val COLUMN_BEER_NAME = "beerName"
        const val COLUMN_BEER_TAG = "beerTag"
        const val COLUMN_BEER_IMAGE = "urlImage"
        const val COLUMN_BEER_RATE = "beerRate"
    }
}