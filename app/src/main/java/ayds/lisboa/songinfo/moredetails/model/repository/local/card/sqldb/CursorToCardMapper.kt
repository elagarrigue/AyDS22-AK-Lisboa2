package ayds.lisboa.songinfo.moredetails.model.repository.local.card.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.home.model.repository.local.spotify.sqldb.RELEASE_DATE_PRECISION_COLUMN
import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import java.sql.SQLException

interface CursorToCardMapper {

    fun map(cursor: Cursor): Card?
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): Card? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    val storedSourceOrdinal = cursor.getInt(getColumnIndexOrThrow(
                        SOURCE_COLUMN
                    ))
                    CardImpl(
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        description = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        infoURL = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        source = Source.values()[storedSourceOrdinal],
                        sourceLogoUrl = getString(getColumnIndexOrThrow(SOURCE_COLUMN))
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
}