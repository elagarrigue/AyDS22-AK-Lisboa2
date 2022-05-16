package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ArtistInfoFormatterTester {

    private val artistInfoFormatter = ArtistInfoFormatterImpl()

    @Test
    fun `given a local artist it should return the description`() {
        val artist: Artist = LastFMArtist(
            "artistName",
            "Paulo Ezequiel Londra Farías, más conocido como Paulo Londra (Córdoba, Argentina; 12 de abril de 1998) es un rapero, cantante, compositor y músico argentino\n" +
                    "\n" +
                    "<a href=\"https://www.last.fm/music/Paulo+Londra\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Paulo+Londra",
            true,
        )

        val result = artistInfoFormatter.getStringArtistInfo(artist)

        val expected =
            "[*]<html><div width=400><font face=\"arial\">Paulo Ezequiel Londra Farías, más conocido como Paulo Londra (Córdoba, Argentina; 12 de abril de 1998) es un rapero, cantante, compositor y músico argentino<br><br><a href=\"https://www.last.fm/music/Paulo+Londra\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local artist it should return the description`() {
        val artist: Artist = LastFMArtist(
            "artistName",
            "Paulo Ezequiel Londra Farías, más conocido como Paulo Londra (Córdoba, Argentina; 12 de abril de 1998) es un rapero, cantante, compositor y músico argentino\n" +
                    "\n" +
                    "<a href=\"https://www.last.fm/music/Paulo+Londra\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Paulo+Londra",
            false,
        )
        val result = artistInfoFormatter.getStringArtistInfo(artist)

        val expected =
            "<html><div width=400><font face=\"arial\">Paulo Ezequiel Londra Farías, más conocido como Paulo Londra (Córdoba, Argentina; 12 de abril de 1998) es un rapero, cantante, compositor y músico argentino<br><br><a href=\"https://www.last.fm/music/Paulo+Londra\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non lastFM artist it should return the artist not found description`() {
        val artist: Artist = mockk()

        val result = artistInfoFormatter.getStringArtistInfo(artist)

        val expected = ""

        Assert.assertEquals(expected, result)
    }



}