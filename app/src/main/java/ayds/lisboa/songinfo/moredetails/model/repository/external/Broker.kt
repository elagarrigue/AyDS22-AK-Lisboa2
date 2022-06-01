package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.ServiceProxy

interface Broker {
    fun getCards(name: String): List<Card>
}

internal class BrokerImpl(
    private val lastFMProxy: ServiceProxy,
    private val nytProxy: ServiceProxy
): Broker {

    override fun getCards(name: String): List<Card> {
        var cardList: MutableList<Card> = mutableListOf()

        val lastFMCard = lastFMProxy.getInfo(name);
        val nytCard = nytProxy.getInfo(name);

        if(cardFound(lastFMCard)){
            cardList.add(lastFMCard!!)
        }
        if(cardFound(nytCard)){
            cardList.add(nytCard!!)
        }

        return cardList
    }

    private fun cardFound(card: Card?): Boolean {
        return card != null
    }
}