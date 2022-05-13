package ayds.lisboa.songinfo.moredetails.model.entities

interface Artist {
    val artistName: String
    val artistInfo: String
    val artistURL: String
    var isLocallyStored: Boolean
}

data class LastFMArtist(
    override val artistName: String,
    override val artistInfo: String,
    override val artistURL: String,
    override var isLocallyStored: Boolean = false
): Artist

object EmptyArtist: Artist{
    override val artistName: String = ""
    override val artistInfo: String = ""
    override val artistURL: String = ""
    override var isLocallyStored: Boolean = false
}