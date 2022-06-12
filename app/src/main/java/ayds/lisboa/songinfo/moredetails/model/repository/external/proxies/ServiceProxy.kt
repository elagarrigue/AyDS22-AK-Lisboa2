package ayds.lisboa.songinfo.moredetails.model.repository.external.proxies

import ayds.lisboa.songinfo.moredetails.model.entities.Card

interface ServiceProxy {
    fun getInfo(name: String): Card
}