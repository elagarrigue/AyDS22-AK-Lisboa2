package ayds.lisboa.songinfo.moredetails.model.repository.local.card

import ayds.lisboa.songinfo.moredetails.model.entities.Card


interface CardLocalStorage {

    fun getCardsByName(name: String): List<Card>

    fun saveCard(card: Card)
}