package ayds.lisboa2.lastFM

import retrofit2.Response

interface LastFMService {

    fun getCard(name: String): Card?

}

internal class LastFMServiceImpl(private val lastFMAPI: LastFMAPI, private val jsonToCardResolver: JsonToCardResolver): LastFMService {

    override fun getCard(name: String): Card? {
        val callResponse = getCardFromService(name)
        return jsonToCardResolver.getCardFromExternalData(callResponse.body(), name)
    }

    private fun getCardFromService(name: String): Response<String?> {
        return lastFMAPI.getArtistInfo(name)?.execute() ?: throw Exception("Artist not found")
    }
}
