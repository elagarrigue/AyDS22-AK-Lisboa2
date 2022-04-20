package ayds.lisboa.songinfo.moredetails.fulllogic

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {

   override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("artist", artist)
        values.put("info", info)
        values.put("source", 1)
        db.insert("artists", null, values)
    }

    fun getInfo(artist: String): String? {

        var cursor = makeQuery(artist)
        return getArtistInfoFromQuery(cursor)
    }

    private fun makeQuery(artist:String) : Cursor{

        val db = this.readableDatabase
        var cursor =  db.query(
           "artists",  // The table to query
            arrayOf("id", "artist", "info"),  // The array of columns to return (pass null to get all)
            "artist  = ?",  // The columns for the WHERE clause
            arrayOf(artist),  // The values for the WHERE clause
            null,  // don't group the rows
            null,  // don't filter by row groups
            "artist DESC" // The sort order
        )
        return cursor
    }

    private fun getArtistInfoFromQuery(cursor: Cursor) : String?{

        var artistInfo: String? = null
        if (cursor.moveToNext()) {
            artistInfo = cursor.getString(
                cursor.getColumnIndexOrThrow("info")
            )
        }
        cursor.close()
        return artistInfo
    }
}