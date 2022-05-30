package ayds.lisboa2.lastFM

data class Card (
    val artistName: String,
    val description: String,
    val infoURL: String,
    val source: String,
    val sourceLogoUrl: String,
    val isLocallyStored: Boolean,
)