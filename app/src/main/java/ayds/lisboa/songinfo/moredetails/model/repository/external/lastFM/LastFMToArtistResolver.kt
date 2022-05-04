package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.StringBuilder


interface LastFMToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?, artistName: String): LastFMArtist?
}

private const val BIOGRAPHY = "bio"
private const val CONTENT = "content"
private const val ARTIST = "artist"
private const val NO_RESULT_MESSAGE = "No Results"

internal class JsonToArtistResolver : LastFMToArtistResolver {

    private lateinit var artistName: String

    //---------------------------------------------------------------------
    //VER ESO DE QUE AL STRING ARTISTINFO HAY QUE APLICARLE LOS METODOS addLineBreaks Y textToHtml
    //DE LA VISTA
    override fun getArtistFromExternalData(serviceData: String?, name: String): LastFMArtist? {
        lateinit var queryArtistInfo: JsonObject
        lateinit var artistBiography: JsonElement
        return try {
            artistName=name
            queryArtistInfo = Gson().fromJson(serviceData, JsonObject::class.java)
            artistBiography = getArtistBiography(queryArtistInfo)
            getStringArtistInfoFromService(artistBiography)
            LastFMArtist(artistName,getStringArtistInfoFromService(artistBiography) )
        }
        catch (e:Exception ){
            null
        }
    }

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].asJsonObject

    private fun getStringArtistInfoFromService(artistBiography: JsonElement) : String {
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