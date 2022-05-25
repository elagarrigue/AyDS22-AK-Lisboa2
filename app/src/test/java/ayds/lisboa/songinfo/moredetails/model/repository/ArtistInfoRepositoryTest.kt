package ayds.lisboa.songinfo.moredetails.model.repository


import ayds.lisboa.songinfo.moredetails.model.entities.EmptyArtist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.lisboa2.lastFM.LastFMService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class ArtistInfoRepositoryTest {

    private val lastFMLocalStorage: LastFMLocalStorage = mockk(relaxUnitFun = true)
    private val lastFMService: LastFMService = mockk(relaxUnitFun = true)

    private val artistRepository: ArtistInfoRepository by lazy {
        ArtistInfoRepositoryImpl(lastFMLocalStorage, lastFMService)
    }

    @Test
    fun `given existing artist by term should return artist and mark it as local`() {
        val artist = LastFMArtist("name", "info", "artistURL", false)
        every { lastFMLocalStorage.getArtistByName("name") } returns artist

        val result = artistRepository.getArtistByName("name")

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        val artist= LastFMArtist("name", "info", "artistUrl",  false)
        every { lastFMLocalStorage.getArtistByName("name") } returns null
        every { lastFMService.getArtist("name") } returns artist

        val result = artistRepository.getArtistByName("name")

        assertEquals(artist, result)
        assertFalse(artist.isLocallyStored)
        verify { lastFMLocalStorage.saveArtist(artist)}
    }

    @Test
    fun `given non existing artist by term should return empty artist`() {
        every { lastFMLocalStorage.getArtistByName("name") } returns null
        every { lastFMService.getArtist("name") } returns null

        val result = artistRepository.getArtistByName("name")

        assertEquals(EmptyArtist, result)
    }

    @Test
    fun `given service exception should return empty artist`() {
        every { lastFMLocalStorage.getArtistByName("name") } returns null
        every { lastFMService.getArtist("name") } throws mockk<Exception>()

        val result = artistRepository.getArtistByName("name")

        assertEquals(EmptyArtist, result)
    }
}