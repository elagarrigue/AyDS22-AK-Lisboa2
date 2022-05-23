package lastFM

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

interface LastFMToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?, artistName: String): LastFMArtist?
}

private const val BIOGRAPHY = "bio"
private const val CONTENT = "content"
private const val ARTIST = "artist"
private const val URL = "url"

internal class JsonToArtistResolver : LastFMToArtistResolver {

    override fun getArtistFromExternalData(serviceData: String?, artistName: String): LastFMArtist? {
        return try {
            createArtist(artistName, getQueryArtistInfo(serviceData))
        }
        catch (e:Exception) {
            null
        }
    }

    private fun getQueryArtistInfo(serviceData: String?) =
        Gson().fromJson(serviceData, JsonObject::class.java)

    private fun createArtist(artistName: String, queryArtistInfo: JsonObject): LastFMArtist =
        LastFMArtist(artistName,getArtistBiography(queryArtistInfo).asString,getArtistBiographyURL(queryArtistInfo))

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].asJsonObject

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)[URL].asString
}