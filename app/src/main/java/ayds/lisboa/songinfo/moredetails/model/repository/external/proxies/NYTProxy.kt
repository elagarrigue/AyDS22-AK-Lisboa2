package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.ak1.newyorktimes.article.external.NYInfoService
import ayds.ak1.newyorktimes.article.external.URL_NYTIMES_LOGO
import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard

internal class NYTProxy (
    private val nytService: NYInfoService
): ServiceProxy {

    override fun getInfo(name: String): Card {
        var nytCard: Card? = null

        try {
            val artistInfoCard = nytService.getArtistInfo(name)

            if(artistInfoCard != null) {
                nytCard = CardImpl(
                    artistInfoCard.artistName,
                    artistInfoCard.description,
                    artistInfoCard.infoURL,
                    Source.NEWYORKTIMES,
                    URL_NYTIMES_LOGO
                )
            }
        }catch(e: Exception){
            nytCard = null
        }

        return nytCard ?: EmptyCard
    }
}