package ayds.lisboa.songinfo.moredetails.model

import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.repository.SongRepository
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.repository.ArtistInfoRepository
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM.LastFMAPI
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.observer.Observable
import ayds.observer.Subject

interface  MoreDetailsModel {
    fun searchArtist(name: String)

    fun getArtistById(id: String): Artist

}

internal class MoreDetailsModelImpl(private val repository: ArtistInfoRepository) : MoreDetailsModel {

    override fun searchArtist(name: String) {
        repository.getArtistByName(name).let {
           //notificar con observable
        }
    }

    override fun getArtistById(id: String): Artist = repository.getArtistById(id)
}

