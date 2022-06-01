package ayds.lisboa.songinfo.moredetails.model.repository.local.card

import ayds.lisboa.songinfo.moredetails.model.entities.Card


interface CardLocalStorage {

    fun getCardByName(name: String): Card?

    fun saveCard(card: Card)
}