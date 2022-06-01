package ayds.lisboa.songinfo.moredetails.model

import android.content.Context
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepository
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.Broker
import ayds.lisboa.songinfo.moredetails.model.repository.external.BrokerImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.LastFMProxy
import ayds.lisboa.songinfo.moredetails.model.repository.external.proxies.ProxyCard
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.sqldb.CursorToCardMapperImpl
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.sqldb.CardLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView
import ayds.lisboa2.lastFM.LastFMInjector
import ayds.lisboa2.lastFM.LastFMService

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel
    
    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val lastFMLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context, CursorToCardMapperImpl()
        )
        val lastFMProxy = LastFMProxy(LastFMInjector.lastFMService)
        val broker: Broker = BrokerImpl(lastFMProxy)

        val repository: CardRepository =
            CardRepositoryImpl(lastFMLocalStorage,broker)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}