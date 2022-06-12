package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa2.lastFM.LASTFM_LOGO
import ayds.lisboa2.lastFM.LastFMArtist
import ayds.lisboa2.lastFM.LastFMService
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class LastFMProxyTester {

    private val lastFM: LastFMService = mockk()
    private val lastfmProxy : LastFMProxy = LastFMProxy(lastFM)

    @Test
    fun `given an artistName, should return information from lastFMService`() {

        val card = CardImpl(
            "name",
            "description",
            "infoURL",
            Source.LASTFM,
            LASTFM_LOGO,
            false
        )

        val lastFMArtist = LastFMArtist(
            "name",
            "description",
            "infoURL"
        )
        every { lastFM.getArtist("name") } returns lastFMArtist

        val result = lastfmProxy.getInfo("name")
        Assert.assertEquals(card, result)
    }

    @Test
    fun `given an artistName that is not in the service, should return EmptyCard`() {
        every { lastFM.getArtist("name") } returns null

        val result = lastfmProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `given a service exception, should return EmptyCard`() {
        every { lastFM.getArtist("name") } throws mockk<Exception>()

        val result = lastfmProxy.getInfo("name")

        Assert.assertEquals(EmptyCard, result)
    }
}