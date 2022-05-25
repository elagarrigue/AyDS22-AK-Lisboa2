package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyArtist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.lisboa2.lastFM.LastFMService
import ayds.lisboa2.lastFM.LastFMArtist as ServiceLastFMArtist

interface ArtistInfoRepository{

    fun getArtistByName(name: String): Artist
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
                    val serviceLastFMArtist = lastFMService.getArtist(name)

                    serviceLastFMArtist?.let {
                        lastFMArtist = LastFMArtist(
                            it.artistName,
                            it.artistInfo,
                            it.artistURL
                        )
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
}