package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist

interface LastFMLocalStorage {

    fun getArtistByName(artist: String): LastFMArtist?

    //fun getArtistById(artist: String): LastFMArtist?

    fun saveArtist(artist: String?, info: String?)
}