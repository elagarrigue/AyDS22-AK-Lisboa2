package ayds.lisboa.songinfo.moredetails.model.repository


import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.CardLocalStorage

import ayds.lisboa2.lastFM.LastFMService

private const val LASTFM_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

interface CardRepository{

    fun getCardByName(name: String): Card?
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val lastFMService: LastFMService
): CardRepository {

    override fun getCardByName(name: String): Card?{
        var card = cardLocalStorage.getCardByName(name)

        when {
            card != null -> markArtistAsLocal(card)
            else -> {
                try {
                    val serviceLastFMArtist = lastFMService.getArtist(name)

                    serviceLastFMArtist?.let {
                        card = Card(
                            it.artistName,
                            it.description,
                            it.infoURL,
                            "LastFM",
                            LASTFM_LOGO,
                        )
                    }
                } catch (e: Exception) {
                    card = null
                }
            }
        }

        return card
    }

    private fun markArtistAsLocal(card: Card) {
        card.isLocallyStored = true
    }
}