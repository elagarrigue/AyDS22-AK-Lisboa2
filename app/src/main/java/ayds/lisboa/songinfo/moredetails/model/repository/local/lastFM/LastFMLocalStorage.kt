package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist

interface LastFMLocalStorage {

    fun getArtist(artist: String): LastFMArtist?

    fun saveArtist(artist: String?, info: String?)
}