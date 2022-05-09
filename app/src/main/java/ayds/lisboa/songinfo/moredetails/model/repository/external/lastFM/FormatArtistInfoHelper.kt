package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import com.google.gson.JsonElement
import java.lang.StringBuilder

private const val NO_RESULT_MESSAGE = "No Results"

interface FormatArtistInfoHelper {
    fun getStingArtistInfo(artistBiography: JsonElement) : String
}

class FormatArtistInfoHelperImpl(private val artistName: String) : FormatArtistInfoHelper {

    override fun getStingArtistInfo(artistBiography: JsonElement) : String {
        var artistInfo: String
        if (existInService(artistBiography)) {
            artistInfo = addLineBreaks(artistBiography)
            artistInfo = textToHtml(artistInfo)
        } else {
            artistInfo = NO_RESULT_MESSAGE
        }
        return artistInfo
    }

    private fun existInService(artistBiography: JsonElement?) =
        artistBiography != null

    private fun addLineBreaks(artistBiography: JsonElement) : String =
        artistBiography.asString.replace("\\n", "\n")

    private fun textToHtml(text: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + artistName.toRegex(), "<b>" + artistName.toUpperCase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }


}