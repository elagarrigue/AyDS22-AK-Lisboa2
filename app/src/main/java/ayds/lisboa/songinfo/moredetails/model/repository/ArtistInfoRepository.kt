package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyArtist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM.LastFMService
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage

private const val LOCAL_DATABASE_PREFIX = "[*]"
private const val ARTIST_NAME = "artistName"

interface ArtistInfoRepository{

    fun getArtistByName(name: String): Artist
    fun getArtistById(id: String): Artist
}

internal class ArtistInfoRepositoryImpl(
    private val lastFMLocalStorage: LastFMLocalStorage,
    private val lastFMService: LastFMService
): ArtistInfoRepository {

    override fun getArtistByName(name: String): Artist {
        var lastFMArtist = lastFMLocalStorage.getArtistByName(name)

        when {
            lastFMArtist != null -> markArtistAsLocal(lastFMArtist)
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