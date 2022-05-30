package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import java.sql.SQLException

interface CursorToLastFMCardMapper {

    fun map(cursor: Cursor): Card?
}

internal class CursorToLastFMCardMapperImpl : CursorToLastFMCardMapper {

    override fun map(cursor: Cursor): Card? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    Card(
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        description = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        infoURL = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        source = getString(getColumnIndexOrThrow(SOURCE_COLUMN)),
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