package ayds.lisboa.songinfo.moredetails.model.repository


import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyArtist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.external.LastFMService
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage

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

        when {
            lastFMArtist != null -> markArtistAsLocal(lastFMArtist)
            //ACA HABRIA QUE AGREGARLE EL [*] A ARTIST INFO, NOSE SI HABRIA QUE HACERLO ACA O EN VIEW
            else -> {
                try {
                    lastFMArtist = lastFMService.getArtist(name)

                    lastFMArtist?.let {
                             lastFMLocalStorage.saveArtist(lastFMArtist!!)
                    }
                } catch (e: Exception) {
                    lastFMArtist = null
                }
            }
        }

        return lastFMArtist ?: EmptyArtist
    }

    private fun markArtistAsLocal(artist: LastFMArtist) {
        artist.isLocallyStored = true
    }

    override fun getArtistById(id: String): Artist {
        return lastFMLocalStorage.getArtistById(id) ?: EmptyArtist
    }

}