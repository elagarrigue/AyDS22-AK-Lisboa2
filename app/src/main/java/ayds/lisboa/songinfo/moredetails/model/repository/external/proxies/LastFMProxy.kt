package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.entities.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa2.lastFM.LASTFM_LOGO
import ayds.lisboa2.lastFM.LastFMService

internal class LastFMProxy (
    private val lastFMService: LastFMService
): ServiceProxy {

    override fun getInfo(name: String): Card {
        var lastFMCard: Card? = null

        try {
            val artistInfoCard = lastFMService.getArtist(name)

            if(artistInfoCard != null) {
                lastFMCard = CardImpl(
                    artistInfoCard.artistName,
                    artistInfoCard.description,
                    artistInfoCard.infoURL,
                    Source.LASTFM,
                    LASTFM_LOGO
                )
            }
        }catch(e: Exception){
            lastFMCard = null
        }

        return lastFMCard ?: EmptyCard
    }
}