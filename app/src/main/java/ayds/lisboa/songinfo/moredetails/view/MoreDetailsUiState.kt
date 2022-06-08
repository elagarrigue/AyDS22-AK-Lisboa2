package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Card

data class MoreDetailsUiState (
    val artistName: String = "",
    var actionsEnabled: MutableList<Boolean> = mutableListOf(false, false, false),
    var cardActual: Card? = null,
    var cards: List<Card> = emptyList(),
)