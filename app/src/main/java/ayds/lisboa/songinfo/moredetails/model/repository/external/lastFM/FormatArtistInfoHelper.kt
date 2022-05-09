package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import com.google.gson.JsonElement
import java.lang.StringBuilder

private const val NO_RESULT_MESSAGE = "No Results"

interface FormatArtistInfoHelper {
    fun getStingArtistInfo(artistBiography: JsonElement) : String
}

class FormatArtistInfoHelperImpl() : FormatArtistInfoHelper {

    override fun getStingArtistInfo(artistBiography: JsonElement) : String {
        var artistInfo: String
        if (existInService(artistBiography)) {
            artistInfo = addLineBreaks(artistBiography)
        } else {
            artistInfo = NO_RESULT_MESSAGE
        }
        return artistInfo
    }

    private fun existInService(artistBiography: JsonElement?) =
        artistBiography != null

    private fun addLineBreaks(artistBiography: JsonElement) : String =
        artistBiography.asString.replace("\\n", "\n")

}