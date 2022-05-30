package ayds.lisboa.songinfo.moredetails.model.repository


import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage

import ayds.lisboa2.lastFM.LastFMService

interface CardRepository{

    fun getCardByName(name: String): Card?
}

internal class CardRepositoryImpl(
    private val lastFMLocalStorage: LastFMLocalStorage,
    private val lastFMService: LastFMService
): CardRepository {

    override fun getCardByName(name: String): Card?{
        var lastFMCard = lastFMLocalStorage.getCardByName(name)

        when {
            lastFMCard != null -> markArtistAsLocal(lastFMCard)
            else -> {
                try {
                    val serviceLastFMArtist = lastFMService.getCard(name)

                    serviceLastFMArtist?.let {
                        lastFMCard = Card(
                            it.artistName,
                            it.description,
                            it.infoURL,
                            it.source,
                            it.sourceLogoUrl,
                            it.isLocallyStored
                        )
                    }
                } catch (e: Exception) {
                    lastFMCard = null
                }
            }
        }

        return lastFMCard
    }

    private fun markArtistAsLocal(card: Card) {
        card.isLocallyStored = true
    }
}