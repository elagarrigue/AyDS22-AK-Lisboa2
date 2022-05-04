package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.external.LastFMService
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val RETROFIT_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

class LastFMServiceImpl(): LastFMService {

    private val lastFMAPI: LastFMAPI = createRetrofit().create(LastFMAPI::class.java)
    private val lastFMToArtistResolver : LastFMToArtistResolver = JsonToArtistResolver()

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    override fun getArtist(name: String): LastFMArtist? {
        val callResponse = getArtistFromService(name)
        return lastFMToArtistResolver.getArtistFromExternalData(callResponse.body(), name)
    }

    private fun getArtistFromService(name: String): Response<String?> {
        return getQueryResponseOfArtistInfoFromService(name)
    }


    private fun getQueryResponseOfArtistInfoFromService(name: String) : Response<String?> =
        lastFMAPI.getArtistInfo(name)!!.execute()
        //VER LO DE LOS ? Y !

}