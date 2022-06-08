package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
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
    fun `proxy`() {

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
}