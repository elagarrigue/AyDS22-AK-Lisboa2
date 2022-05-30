package ayds.lisboa.songinfo.moredetails.model

import android.content.Context
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepository
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb.CursorToLastFMCardMapperImpl
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb.LastFMLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView
import ayds.lisboa2.lastFM.LastFMInjector
import ayds.lisboa2.lastFM.LastFMService

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel
    
    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val lastFMLocalStorage: LastFMLocalStorage = LastFMLocalStorageImpl(
            moreDetailsView as Context, CursorToLastFMCardMapperImpl()
        )
        val lastFMService: LastFMService = LastFMInjector.lastFMService

        val repository: CardRepository =
            CardRepositoryImpl(lastFMLocalStorage, lastFMService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}