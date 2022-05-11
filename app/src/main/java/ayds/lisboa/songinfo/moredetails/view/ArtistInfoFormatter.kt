package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import java.lang.StringBuilder

private const val LOCAL_DATABASE_PREFIX = "[*]"

interface ArtistInfoFormatter {

    fun getStringArtistInfo(artist: Artist) : String
}

class ArtistInfoFormatterImpl: ArtistInfoFormatter{

    override fun getStringArtistInfo(artist: Artist): String {
        var artistInfo: String
        artistInfo = addLineBreaks(artist.artistInfo)
        artistInfo = textToHtml(artistInfo, artist.artistName)
        artistInfo = addPrefix(artist.isLocallyStored, artistInfo)
        return artistInfo
    }

    private fun addLineBreaks(artistInfo: String) : String =
        artistInfo.replace("\\n", "\n")

    private fun textToHtml(text: String, artistName: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + artistName.toRegex(), "<b>" + artistName.uppercase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    private fun addPrefix(isLocallyStored: Boolean, artistInfo: String) =
        if (isLocallyStored) {
            "$LOCAL_DATABASE_PREFIX${artistInfo}"
        }
        else {
            artistInfo
        }
}