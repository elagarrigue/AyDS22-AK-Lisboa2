package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage

class LastFMLocalStorageImpl(
    context: Context?,
    private val cursorToLastFMArtistMapper: CursorToLastFMArtistMapperImpl,
) : LastFMLocalStorage, SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

   override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun saveArtist(artist: Artist) {
        this.writableDatabase.insert(ARTIST_TABLE_NAME, null, getContentValues(artist))
    }

    private fun getContentValues(artist: Artist): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist.artistName)
        values.put(INFO_COLUMN, artist.artistInfo)
        values.put(URL_COLUMN, artist.artistURL)
        return values
    }

    override fun getArtistByName(name: String): LastFMArtist? {
        val cursor = this.readableDatabase.query(
            ARTIST_TABLE_NAME,
            arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN),
            "$ARTIST_COLUMN  = ?",
            arrayOf(name),
            null,
            null,
            "$ARTIST_COLUMN DESC"
        )
        return cursorToLastFMArtistMapper.map(cursor)
    }

    override fun getArtistById(id: String): LastFMArtist? {
        val cursor = readableDatabase.query(
            ARTIST_TABLE_NAME,
            arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN),
            "$ID_COLUMN  = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        return cursorToLastFMArtistMapper.map(cursor)
    }
}