package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.moredetails.model.entities.Source

data class MoreDetailsUiState (
    val artistName: String = "",
    var actionsEnabled: MutableList<Boolean> = mutableListOf(false, false, false),
    var cardActual: Card? = null,
    var cards: List<Card> = emptyList(),
){
    fun getCard(i : Int) : Card {
        return cards.firstOrNull{it.source == Source.values()[i]} ?: EmptyCard
    }
}