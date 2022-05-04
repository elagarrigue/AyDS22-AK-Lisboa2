package ayds.lisboa.songinfo.moredetails.model.repository

import android.content.Intent
import android.net.Uri
import ayds.lisboa.songinfo.home.model.entities.EmptySong
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.entities.SpotifySong
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyArtist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.external.LastFMService
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM.LastFMAPI
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFMService
import ayds.lisboa.songinfo.moredetails.view.*
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException


private const val LOCAL_DATABASE_PREFIX = "[*]"
private const val ARTIST_NAME = "artistName"

interface ArtistInfoRepository{

    fun getArtistByName(name: String): Artist
    fun getArtistById(id: String): Artist
}


internal class ArtistInfoRepositoryImpl: ArtistInfoRepository {

    private lateinit var lastFMLocalStorage: LastFMLocalStorage
    private lateinit var lastFMService: LastFMService

   // private lateinit var artistName: String


    override fun getArtistByName(name: String): Artist {
        var lastFMArtist = lastFMLocalStorage.getArtistByName(name)
        /*if (existInDataBase(artistInfo)) {
            artistInfo = "$LOCAL_DATABASE_PREFIX$artistInfo"
        } else {
            artistInfo = getArtistInfoFromService()
            saveArtistInDataBase(artistInfo)
        }*/

        when {
            lastFMArtist != null -> markArtistAsLocal(lastFMArtist)
            //ACA HABRIA QUE AGREGARLE EL [*] A ARTIST INFO, NOSE SI HABRIA QUE HACERLO ACA O EN VIEW
            else -> {
                try {
                    lastFMArtist = lastFMService.getArtist(name)

                    lastFMArtist?.let {
                        when { //PREGUNTAR
                            it.isSavedSong() -> spotifyLocalStorage.updateSongTerm(term, it.id)
                            else -> spotifyLocalStorage.insertSong(term, it)
                        }
                    }
                } catch (e: Exception) {
                    spotifySong = null
                }
            }
        }

        return artist ?: EmptyArtist
    }

    private fun markArtistAsLocal(artist: LastFMArtist) {
        artist.isLocallyStored = true
    }

    override fun getArtistById(id: String): Artist {
        return lastFMLocalStorage.getArtistById(id) ?: EmptyArtist
    }

    private fun LastFMArtist.isSavedSong() = lastFMLocalStorage.getSongById(id) != null

    private fun getArtistInfoFromDataBase(name:String) =
        lastFMLocalStorage.getInfo(name)

    private fun existInDataBase(artistInfo: String?) =
        artistInfo != null

    private fun getArtistInfoFromService(): String {
        lateinit var queryArtistInfo: JsonObject
        lateinit var artistBiography: JsonElement
        return try {
            queryArtistInfo = getQueryBodyOfArtistInfoFromService()
            artistBiography = getArtistBiography(queryArtistInfo)
            setOnClickerListenerToOpenURLButton(queryArtistInfo)
            getStringArtistInfoFromService(artistBiography)
        } catch (e1: IOException) {
            e1.printStackTrace()
            ""
        }
    }

    private fun getQueryBodyOfArtistInfoFromService() =
        Gson().fromJson(getQueryResponseOfArtistInfoFromService().body(), JsonObject::class.java)

    private fun getQueryResponseOfArtistInfoFromService() : Response<String> =
        lastFMAPI.getArtistInfo(artistName).execute()

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun setOnClickerListenerToOpenURLButton(queryArtistInfo: JsonObject) {
        openUrlButton.setOnClickListener {
            onClickActionOpenURLButton(queryArtistInfo)
        }
    }

    private fun onClickActionOpenURLButton(queryArtistInfo: JsonObject){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(getArtistBiographyURL(queryArtistInfo))
        startActivity(intent)
    }

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)[URL].asString

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

    private fun saveArtistInDataBase(artistInfo: String) {
        dataBase.saveArtist(artistName, artistInfo)
    }


}