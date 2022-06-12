package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.repository.external.Broker
import ayds.lisboa.songinfo.moredetails.model.repository.external.BrokerImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.LastFMProxy
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.NYTProxy
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.ServiceProxy
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.WikipediaProxy
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class BrokerTest {

    private val lastFMProxy : LastFMProxy = mockk(relaxUnitFun = true)
    private val nytProxy: NYTProxy = mockk(relaxUnitFun = true)
    private val wkpProxy: WikipediaProxy = mockk(relaxUnitFun = true)

    private val proxies: List<ServiceProxy> = arrayListOf(
        lastFMProxy, nytProxy, wkpProxy
    )

    private val broker: Broker = BrokerImpl(proxies)

    private val lastFMCard = CardImpl(
        "name",
        "description",
        "infoURL",
        Source.LASTFM,
        "logoUrl",
        false
    )

    private val nytCard = CardImpl(
        "name",
        "description",
        "infoURL",
        Source.NEWYORKTIMES,
        "logoUrl",
        false
    )

    private val wkpCard = CardImpl(
        "name",
        "description",
        "infoURL",
        Source.WIKIPEDIA,
        "logoUrl",
        false
    )

    @Test
    fun `given an artistName that is in all services, should return a list of CardImpl`() {
        val cards: List<Card> = arrayListOf(
            lastFMCard, nytCard, wkpCard
        )

        every { lastFMProxy.getInfo("name") } returns lastFMCard
        every { nytProxy.getInfo("name") } returns nytCard
        every { wkpProxy.getInfo("name") } returns wkpCard

        val result = broker.getCards("name")

        assertEquals(cards, result)
    }

    @Test
    fun `given an artistName that is not in any service, should return an empty list`() {
        val cards = arrayListOf<Card>()

        every { lastFMProxy.getInfo("name") } returns EmptyCard
        every { nytProxy.getInfo("name") } returns EmptyCard
        every { wkpProxy.getInfo("name") } returns EmptyCard

        val result = broker.getCards("name")

        assertEquals(cards,result)
    }

    @Test
    fun `given an artistName that is in some services, should return a list with some CardImpl`() {
        val cards: List<Card> = arrayListOf(
            nytCard, wkpCard
        )

        every { lastFMProxy.getInfo("name") } returns EmptyCard
        every { nytProxy.getInfo("name") } returns nytCard
        every { wkpProxy.getInfo("name") } returns wkpCard

        val result = broker.getCards("name")

        assertEquals(cards, result)
    }
}
