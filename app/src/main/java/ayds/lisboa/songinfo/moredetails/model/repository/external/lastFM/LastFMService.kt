package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import retrofit2.Response

interface LastFMService {

    fun getArtist(name: String): LastFMArtist?

}

internal class LastFMServiceImpl(private val lastFMAPI: LastFMAPI, private val lastFMToArtistResolver: LastFMToArtistResolver): LastFMService {

    override fun getArtist(name: String): LastFMArtist? {
        val callResponse = getArtistFromService(name)
        return lastFMToArtistResolver.getArtistFromExternalData(callResponse.body(), name)
    }

    private fun getArtistFromService(name: String): Response<String?> {
        return lastFMAPI.getArtistInfo(name)?.execute() ?: throw Exception("Artist not found")
    }
}
