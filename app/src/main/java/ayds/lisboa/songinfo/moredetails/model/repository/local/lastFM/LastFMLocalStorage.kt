package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist

interface LastFMLocalStorage {

    fun getArtistByName(name: String): LastFMArtist?

    fun saveArtist(artist: Artist)
}