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

    private lateinit var artistName: String
    private var cards: MutableList<Card> = mutableListOf()

    override fun getCards(name: String): List<Card> {
        this.artistName = name
        getCardsFromProxies()

        return cards
    }

    private fun getCardsFromProxies() {
        proxies.forEach {
            makeCardsList(getProxysCard(it))
        }
    }

    private fun getProxysCard(serviceProxy: ServiceProxy): Card = serviceProxy.getInfo(this.artistName)

    private fun makeCardsList(card: Card) {
        when (card) {
            is CardImpl -> this.cards.add(card)
        }
    }
}