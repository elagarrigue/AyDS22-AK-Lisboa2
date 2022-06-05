package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard

interface CardHandler {
    fun navigateToLastFMActivity()
    fun navigateToWikipediaActivity()
    fun navigateToNYTActivity()
}

internal class CardHandlerImpl (
    private val moreDetailsView: MoreDetailsView,
    private val cards: List<Card>) : CardHandler {
    override fun navigateToLastFMActivity() {
        moreDetailsView.openCardActivity(getLastFMCard())
    }

    override fun navigateToWikipediaActivity() {
        moreDetailsView.openCardActivity(getWikipediaCard())
    }
    override fun navigateToNYTActivity() {
        moreDetailsView.openCardActivity(getNYTCard())
    }

    private fun getLastFMCard() : Card {
        return cards.firstOrNull{it.source == Source.LASTFM} ?: EmptyCard
    }

    private fun getWikipediaCard() : Card {
        return cards.firstOrNull{it.source == Source.WIKIPEDIA} ?: EmptyCard
    }

    private fun getNYTCard() : Card {
        return cards.firstOrNull{it.source == Source.NEWYORKTIMES} ?: EmptyCard
    }
}