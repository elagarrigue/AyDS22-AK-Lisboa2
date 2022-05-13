package ayds.lisboa.songinfo.moredetails.model

import android.content.Context
import ayds.lisboa.songinfo.moredetails.model.repository.ArtistInfoRepository
import ayds.lisboa.songinfo.moredetails.model.repository.ArtistInfoRepositoryImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM.LastFMInjector
import ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM.LastFMService
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.LastFMLocalStorage
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb.CursorToLastFMArtistMapperImpl
import ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb.LastFMLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel
    
    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val lastFMLocalStorage: LastFMLocalStorage = LastFMLocalStorageImpl(
            moreDetailsView as Context, CursorToLastFMArtistMapperImpl()
        )
        val lastFMService: LastFMService = LastFMInjector.lastFMService

        val repository: ArtistInfoRepository =
            ArtistInfoRepositoryImpl(lastFMLocalStorage, lastFMService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}