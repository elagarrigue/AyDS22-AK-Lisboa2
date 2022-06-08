package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.ServiceProxy

interface Broker {
    fun getCards(name: String): List<Card>
}

internal class BrokerImpl(
    private val proxies: List<ServiceProxy>
): Broker {

    override fun getCards(name: String): List<Card> {
        var cards: MutableList<Card> = mutableListOf()
        proxies.forEach {
            makeCardsList(it.getInfo(name),cards)
        }
        return cards
    }

    private fun makeCardsList(card: Card, cards: MutableList<Card>) {
        when (card) {
            is CardImpl -> cards.add(card)
        }
    }
}