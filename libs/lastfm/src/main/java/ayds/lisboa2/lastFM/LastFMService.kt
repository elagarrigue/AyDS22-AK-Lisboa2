package ayds.lisboa2.lastFM

import retrofit2.Response

interface LastFMService {

    fun getArtist(name: String): LastFMArtist?

}

internal class LastFMServiceImpl(private val lastFMAPI: LastFMAPI, private val jsonToCardResolver: JsonToArtistResolver): LastFMService {

    override fun getArtist(name: String): LastFMArtist? {
        val callResponse = getArtistInfoFromService(name)
        return jsonToCardResolver.getArtistFromExternalData(callResponse.body(), name)
    }

    private fun getArtistInfoFromService(name: String): Response<String?> {
        return lastFMAPI.getArtistInfo(name)?.execute() ?: throw Exception("Artist not found")
    }
}
