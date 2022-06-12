package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester1.wikipedia.WikipediaArtistInfo
import ayds.winchester1.wikipedia.WikipediaService
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class WikipediaProxyTester {

    private val wikipediaServ: WikipediaService = mockk()
    private val wkpProxy : WikipediaProxy = WikipediaProxy(wikipediaServ)

    @Test
    fun `given an artistName, should return information from WikipediaService`() {

        val card = CardImpl(
            "name",
            "description",
            "infoURL",
            Source.WIKIPEDIA,
            "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png",
            false
        )

        val wkpArtist = WikipediaArtistInfo(
            "description",
            "infoURL",
            card.sourceLogoUrl
        )
        every {wikipediaServ.getArtistInfo("name") } returns wkpArtist

        val result = wkpProxy.getInfo("name")
        Assert.assertEquals(card, result)
    }

    @Test
    fun `given an artistName that is not in the service, should return EmptyCard`() {
        every { wikipediaServ.getArtistInfo("name") } returns null

        val result = wkpProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `given a service exception, should return EmptyCard`() {
        every { wikipediaServ.getArtistInfo("name") } throws mockk<Exception>()

        val result = wkpProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }
}