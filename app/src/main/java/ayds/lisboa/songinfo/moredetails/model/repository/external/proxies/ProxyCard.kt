package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.entities.Card

interface ProxyCard {
    fun getCard(name: String): Card?
}