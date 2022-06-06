package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Card

const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

data class MoreDetailsUiState (
    val artistName: String = "",
    var actionsEnabled: MutableList<Boolean> = mutableListOf(false, false, false),
    var cardActual: Card? = null,
    var cards: List<Card> = emptyList(),
)