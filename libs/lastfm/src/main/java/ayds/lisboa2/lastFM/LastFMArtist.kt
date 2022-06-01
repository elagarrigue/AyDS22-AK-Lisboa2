package ayds.lisboa2.lastFM

const val LASTFM_LOGO = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

data class LastFMArtist (
    val artistName: String,
    val description: String,
    val infoURL: String,
)