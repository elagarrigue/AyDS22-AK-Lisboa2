package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Card

data class CardUiState(
    var card: Card,
    var enable: Boolean
)
