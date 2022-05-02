package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage

class LastFMLocalStorageImpl(context: Context?) : LastFMLocalStorage, SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

   override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun saveArtist(artist: String?, info: String?) {
        this.writableDatabase.insert(ARTIST_TABLE_NAME, null, getContentValues(artist,info))
    }

    private fun getContentValues(artist: String?,info: String?): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist)
        values.put(INFO_COLUMN, info)
        values.put(SOURCE_COLUMN, 1)
        return values
    }

    override fun getInfo(name: String): String? = getArtistInfoFromQuery(makeQuery(artist))

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