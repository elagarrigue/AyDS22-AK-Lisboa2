package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
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

    private lateinit var artistName: String
    private lateinit var queryArtistInfo: JsonObject

    override fun getArtistFromExternalData(serviceData: String?, name: String): LastFMArtist? {
        return try {
            initArtistName(name)
            initQueryArtistInfo(serviceData)
            createArtist()
        }
        catch (e:Exception) {
            null
        }
    }

    private fun initArtistName(name: String) {
        artistName = name
    }

    private fun initQueryArtistInfo(serviceData: String?) {
        queryArtistInfo = Gson().fromJson(serviceData, JsonObject::class.java)
    }

    private fun createArtist(): LastFMArtist =
        LastFMArtist(artistName,getArtistBiography(queryArtistInfo).asString,getArtistBiographyURL(queryArtistInfo))

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].asJsonObject

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)[URL].asString
}