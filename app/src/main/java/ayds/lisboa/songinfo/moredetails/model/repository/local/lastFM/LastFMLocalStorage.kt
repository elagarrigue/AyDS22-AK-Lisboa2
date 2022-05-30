package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.Card


interface LastFMLocalStorage {

    fun getCardByName(name: String): Card?

    fun saveCard(card: Card)
}