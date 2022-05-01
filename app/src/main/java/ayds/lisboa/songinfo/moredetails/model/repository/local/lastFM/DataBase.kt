package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

const val DATABASE_NAME = "dictionary.db"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val SOURCE_COLUMN = "source"
const val ID_COLUMN = "id"
const val ARTIST_TABLE_NAME = "artists"
const val ARTIST_TABLE_QUERY = "create table $ARTIST_TABLE_NAME ($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $ARTIST_COLUMN string, $INFO_COLUMN string, $SOURCE_COLUMN integer)"

class DataBase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

   override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            ARTIST_TABLE_QUERY
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        this.writableDatabase.insert(ARTIST_TABLE_NAME, null, getContentValues(artist,info))
    }

    private fun getContentValues(artist: String?,info: String?): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist)
        values.put(INFO_COLUMN, info)
        values.put(SOURCE_COLUMN, 1)
        return values
    }

    fun getInfo(artist: String): String? = getArtistInfoFromQuery(makeQuery(artist))

    private fun makeQuery(artist: String): Cursor =
        this.readableDatabase.query(
            ARTIST_TABLE_NAME,
            arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN),
            "$ARTIST_COLUMN  = ?",
            arrayOf(artist),  
            null,
            null,
            "$ARTIST_COLUMN DESC"
        )

    private fun getArtistInfoFromQuery(cursor: Cursor) : String?{
        var artistInfo: String? = null
        if (cursor.moveToNext()) {
            artistInfo = cursor.getString(
                cursor.getColumnIndexOrThrow(INFO_COLUMN)
            )
        }
        cursor.close()
        return artistInfo
    }
}