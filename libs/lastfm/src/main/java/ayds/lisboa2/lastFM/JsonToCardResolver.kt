package ayds.lisboa2.lastFM

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

interface JsonToCardResolver {
    fun getCardFromExternalData(serviceData: String?, artistName: String): Card?
}

private const val BIOGRAPHY = "bio"
private const val CONTENT = "content"
private const val ARTIST = "artist"
private const val URL = "url"
private const val SOURCE = "LastFM"
private const val LASTFM_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

internal class JsonToCardResolverImpl : JsonToCardResolver {

    override fun getCardFromExternalData(serviceData: String?, artistName: String): Card? {
        return try {
            createCard(artistName, getQueryArtistInfo(serviceData))
        }
        catch (e:Exception) {
            null
        }
    }

    private fun getQueryArtistInfo(serviceData: String?) =
        Gson().fromJson(serviceData, JsonObject::class.java)

    private fun createCard(artistName: String, queryArtistInfo: JsonObject): Card =
        Card(artistName, getArtistBiography(queryArtistInfo).asString, getArtistBiographyURL(queryArtistInfo), SOURCE, LASTFM_LOGO, false)

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].asJsonObject

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)[URL].asString
}