package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.LastFMProxy
import java.util.*

interface Broker {
    fun getCards(name: String): List<Card>
}

internal class BrokerImpl(
    private val lastFMProxy: LastFMProxy
): Broker {

    override fun getCards(name: String): List<Card> {
        var cardList: MutableList<Card> = mutableListOf()

        val lastFMCard = lastFMProxy.getCard(name);

        if(cardFound(lastFMCard)){
            cardList.add(lastFMCard!!)
        }

        return cardList
    }

    private fun cardFound(card: Card?): Boolean {
        return card != null
    }
}