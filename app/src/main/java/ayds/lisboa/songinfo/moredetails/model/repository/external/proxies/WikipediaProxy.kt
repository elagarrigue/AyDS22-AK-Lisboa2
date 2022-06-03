package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.winchester1.wikipedia.WikipediaCardService

internal class WikipediaProxy (
    private val wkpService: WikipediaCardService
): ServiceProxy {

    override fun getInfo(name: String): Card? {
        var wkpCard: Card? = null

        try {
            val artistInfoCard = wkpService.getCard(name)

            if(artistInfoCard != null) {
                wkpCard = CardImpl(
                    name,
                    artistInfoCard.description,
                    artistInfoCard.infoURL,
                    Source.LASTFM,
                    artistInfoCard.sourceLogoURL,
                )
            }
        }catch(e: Exception){
            wkpCard = null
        }

        return wkpCard
    }
}
