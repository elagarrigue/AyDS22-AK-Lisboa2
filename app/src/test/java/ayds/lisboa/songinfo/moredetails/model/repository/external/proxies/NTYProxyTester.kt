package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.ak1.newyorktimes.article.external.NYArticle
import ayds.ak1.newyorktimes.article.external.NYInfoService
import ayds.ak1.newyorktimes.article.external.URL_NYTIMES_LOGO
import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
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
}