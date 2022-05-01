package ayds.lisboa.songinfo.moredetails.model.entities

interface Artist {
    val id: Integer
    val artistName: String
    val artistInfo: String
    val source: Integer
}

data class LastFMArtist(
    override val id: Integer,
    override val artistName: String,
    override val artistInfo: String,
    override val source: Integer
): Artist