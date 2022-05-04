package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist

interface LastFMService {

    fun getArtist(name: String): LastFMArtist?

}