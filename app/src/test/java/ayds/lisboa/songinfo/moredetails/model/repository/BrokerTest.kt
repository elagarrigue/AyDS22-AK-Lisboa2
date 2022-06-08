package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.Broker
import ayds.lisboa.songinfo.moredetails.model.repository.external.BrokerImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.ServiceProxy
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class BrokerTest {

    private val proxy: ServiceProxy = mockk()
    private val listProxies: List<ServiceProxy> = arrayListOf(
        proxy, proxy, proxy
    )

    private val broker: Broker = BrokerImpl(listProxies)

    @Test
    fun `broker`() {
        val card: CardImpl = mockk()
        val cards: List<Card> = arrayListOf(
            card, card, card
        )
        every { proxy.getInfo("name") } returns card

        val result = broker.getCards("name")

        assertEquals(cards, result)
    }
}
