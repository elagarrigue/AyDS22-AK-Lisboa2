package ayds.lisboa2.lastFM

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val RETROFIT_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object LastFMInjector {

    private val lastFMAPI: LastFMAPI = createRetrofit().create(LastFMAPI::class.java)
    private val jsonToCardResolver : JsonToCardResolver = JsonToCardResolverImpl()
    val lastFMService: LastFMService = LastFMServiceImpl(lastFMAPI, jsonToCardResolver)

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
}