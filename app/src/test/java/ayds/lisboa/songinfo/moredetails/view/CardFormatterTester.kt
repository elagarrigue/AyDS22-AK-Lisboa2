package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import org.junit.Assert
import org.junit.Test

class CardFormatterTester {

    private val cardFormatter = CardFormatterImpl()

    @Test
    fun `given a local artist it should return the description`() {
        val card: Card = CardImpl(
            "artistName",
            "this is the artist description",
            "url",
            Source.LASTFM,
            "logoUrl",
            true
        )

        val result = cardFormatter.getStringArtistInfo(card)

        val expected =
            "[*]<html><div width=400><font face=\"arial\">this is the artist description</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local artist it should return the description`() {
        val card: Card = CardImpl(
            "artistName",
            "this is the artist description",
            "url",
            Source.LASTFM,
            "logoUrl",
            false
        )

        val result = cardFormatter.getStringArtistInfo(card)

        val expected =
            "<html><div width=400><font face=\"arial\">this is the artist description</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non existing artist it should return the artist not found description`() {
        val card: Card = EmptyCard

        val result = cardFormatter.getStringArtistInfo(card)

        val expected = "<html><div width=400><font face=\"arial\"></font></div></html>"

        Assert.assertEquals(expected, result)
    }
}