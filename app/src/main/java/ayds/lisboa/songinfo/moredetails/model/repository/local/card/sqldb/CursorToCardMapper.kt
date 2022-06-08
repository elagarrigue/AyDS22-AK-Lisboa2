package ayds.lisboa.songinfo.moredetails.model.repository.local.card.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import java.sql.SQLException

interface CursorToCardMapper {

    fun map(cursor: Cursor): List<Card>
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): List<Card> {
        var cards: MutableList<Card> = mutableListOf()
        var card: CardImpl
        try {
            with(cursor) {
                while (moveToNext()) {
                    val storedSourceOrdinal = cursor.getInt(
                        getColumnIndexOrThrow(
                            SOURCE_COLUMN
                        )
                    )
                    card = CardImpl(
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        description = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        infoURL = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        source = Source.values()[storedSourceOrdinal],
                        sourceLogoUrl = getString(getColumnIndexOrThrow(SOURCE_COLUMN))
                    )
                    cards.add(card)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return cards
    }
}