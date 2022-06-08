package ayds.lisboa.songinfo.moredetails.view

import org.junit.Assert
import org.junit.Test

class CardFormatterTester {

    private val cardFormatter = CardFormatterImpl()
    private val name = "artistName"
    private val description = "this is the artist description"

    @Test
    fun `given a local artist it should return the description`() {

        val result = cardFormatter.getStringCardInfo(description, name, true)

        val expected =
            "[*]<html><div width=400><font face=\"arial\">this is the artist description</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local artist it should return the description`() {

        val result = cardFormatter.getStringCardInfo(description, name, false)

        val expected =
            "<html><div width=400><font face=\"arial\">this is the artist description</font></div></html>"

        Assert.assertEquals(expected, result)
    }

}