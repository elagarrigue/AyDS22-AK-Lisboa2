package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM

interface LastFMLocalStorage {

    fun getInfo(artist: String): String?

    fun saveArtist(artist: String?, info: String?)
}