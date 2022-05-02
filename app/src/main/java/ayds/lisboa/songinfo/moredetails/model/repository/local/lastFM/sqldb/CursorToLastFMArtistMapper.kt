package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import java.sql.SQLException

interface CursorToLastFMArtistMapper {

    fun map(cursor: Cursor): LastFMArtist?
}

class CursorToLastFMArtistMapperImpl : CursorToLastFMArtistMapper {

    override fun map(cursor: Cursor): LastFMArtist? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    LastFMArtist(
                        id = getString(getColumnIndexOrThrow(ID_COLUMN)),
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        artistInfo = getString(getColumnIndexOrThrow(INFO_COLUMN)),
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