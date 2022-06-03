package ayds.lisboa.songinfo.moredetails.model.entities

import ayds.lisboa.songinfo.moredetails.model.Source


interface Card {
    val artistName: String
    val description: String
    val infoURL: String
    val source: Source
    val sourceLogoUrl: String
    var isLocallyStored: Boolean
}

data class CardImpl (
    override val artistName: String,
    override val description: String,
    override val infoURL: String,
    override val source: Source,
    override val sourceLogoUrl: String,
    override var isLocallyStored: Boolean = false
): Card

object EmptyCard: Card {
    override val artistName: String = ""
    override val description: String = ""
    override val infoURL: String = ""
    override val source: Source = Source.EMPTY
    override val sourceLogoUrl: String = ""
    override var isLocallyStored: Boolean = false
}