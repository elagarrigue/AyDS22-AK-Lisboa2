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
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        this.writableDatabase.insert("artists", null, getContentValues(artist,info))
    }

    private fun getContentValues(artist: String?,info: String?): ContentValues {
        val values = ContentValues()
        values.put("artist", artist)
        values.put("info", info)
        values.put("source", 1)
        return values
    }

    fun getInfo(artist: String): String? = getArtistInfoFromQuery(makeQuery(artist))

    private fun makeQuery(artist: String): Cursor =
        this.readableDatabase.query(
            "artists",
            arrayOf("id", "artist", "info"),
            "artist  = ?",
            arrayOf(artist),  
            null,
            null,
            "artist DESC"
        )


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