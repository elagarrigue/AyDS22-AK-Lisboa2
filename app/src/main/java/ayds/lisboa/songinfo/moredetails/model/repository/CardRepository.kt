package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.external.Broker
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.CardLocalStorage

interface CardRepository{

    fun getArtistsInfo(name: String): List<Card>
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
): CardRepository {

    override fun getArtistsInfo(name: String): List<Card>{
        var artistsCards: List<Card>? = null// cardLocalStorage.getCardByName(name)

        when {
            artistsCards != null -> markCardsAsLocal(artistsCards)
            else -> {
                artistsCards = broker.getCards(name)
                saveCardsInDatabase(artistsCards)
            }
        }

        return artistsCards
    }

    private fun markCardsAsLocal(cards: List<Card>) {
        for(card in cards){
            card.isLocallyStored = true
        }
    }

    private fun saveCardsInDatabase(cards: List<Card>){
        for(card in cards){
            card?.let{
                cardLocalStorage.saveCard(card)
            }
        }
    }
}