package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.home.view.HomeView
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView


interface MoreDetailsController {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
    fun searchArtist(name: String): Artist
}

class MoreDetailsControllerImpl(
    private val moreDetailsModel: MoreDetailsModel
) : MoreDetailsController {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        //aca va lo del observable
    }

    override fun searchArtist(name: String) =
        moreDetailsModel.searchArtist(name)

}