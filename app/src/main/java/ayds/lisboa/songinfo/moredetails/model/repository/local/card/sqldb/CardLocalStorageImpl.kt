package ayds.lisboa.songinfo.moredetails.model.repository.local.card.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.CardLocalStorage

internal class CardLocalStorageImpl(
    context: Context?,
    private val cursorToCardMapper: CursorToCardMapper,
) : CardLocalStorage, SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

   override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun saveCard(card: Card) {
        this.writableDatabase.insert(CARD_TABLE_NAME, null, getContentValues(card))
    }

    private fun getContentValues(artist: Card): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist.artistName)
        values.put(INFO_COLUMN, artist.description)
        values.put(URL_COLUMN, artist.infoURL)
        values.put(SOURCE_COLUMN, artist.source)
        return values
    }

    override fun getCardByName(name: String): Card? {
        val cursor = this.readableDatabase.query(
            CARD_TABLE_NAME,
            arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN, SOURCE_COLUMN),
            "$ARTIST_COLUMN  = ?",
            arrayOf(name),
            null,
            null,
            "$ARTIST_COLUMN DESC"
        )
        return cursorToCardMapper.map(cursor)
    }
}