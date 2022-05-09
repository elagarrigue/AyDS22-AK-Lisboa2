package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import java.lang.StringBuilder

interface ArtistInfoFormatter {

    fun getHtmlArtistInfo(artist: Artist) : String
}

class ArtistInfoFormatterImpl() : ArtistInfoFormatter{

    override fun getHtmlArtistInfo(artist: Artist): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = artist.artistInfo
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + artist.artistName.toRegex(), "<b>" + artist.artistName.toUpperCase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

}