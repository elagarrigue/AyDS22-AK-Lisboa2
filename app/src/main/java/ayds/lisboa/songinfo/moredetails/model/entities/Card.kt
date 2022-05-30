package ayds.lisboa.songinfo.moredetails.model.entities

data class Card (
    val artistName: String,
    val description: String,
    val infoURL: String,
    val source: String,
    val sourceLogoUrl: String,
    var isLocallyStored: Boolean = false
)