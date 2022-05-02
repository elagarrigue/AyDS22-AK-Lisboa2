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

    private lateinit var lastFMAPI: LastFMAPI

    override fun getArtist(name: String): LastFMArtist? {
        TODO("Not yet implemented")
    }

    private fun initLastFMAPI(){
        lastFMAPI = createRetrofit().create(LastFMAPI::class.java)
    }

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    private fun getQueryBodyOfArtistInfoFromService() =
        Gson().fromJson(getQueryResponseOfArtistInfoFromService().body(), JsonObject::class.java)

    private fun getQueryResponseOfArtistInfoFromService() : Response<String> =
        lastFMAPI.getArtistInfo(artistName).execute()

}