package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.ak1.newyorktimes.article.external.NYArticle
import ayds.ak1.newyorktimes.article.external.NYInfoService
import ayds.ak1.newyorktimes.article.external.URL_NYTIMES_LOGO
import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class NYTProxyTester {

    private val nytService: NYInfoService = mockk()
    private val nytProxy : NYTProxy = NYTProxy(nytService)

    @Test
    fun `given an artistName, should return information from NYTService`() {

        val card = CardImpl(
            "name",
            "description",
            "infoURL",
            Source.NEWYORKTIMES,
            URL_NYTIMES_LOGO,
            false
        )

        val nytArtist = NYArticle(
            "description",
            "infoURL",
            "name",
            card.sourceLogoUrl
        )
        every {nytService.getArtistInfo("name") } returns nytArtist

        val result = nytProxy.getInfo("name")
        Assert.assertEquals(card, result)
    }

    @Test
    fun `given an artistName that is not in the service, should return EmptyCard`() {
        every { nytService.getArtistInfo("name") } returns null

        val result = nytProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `given a service exception, should return EmptyCard`() {
        every { nytService.getArtistInfo("name") } throws mockk<Exception>()

        val result = nytProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }
}