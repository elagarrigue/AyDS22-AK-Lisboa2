package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsUiEvent
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView
import ayds.observer.Observer


interface MoreDetailsController {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
    fun searchArtist()
}

class MoreDetailsControllerImpl(
    private val moreDetailsModel: MoreDetailsModel
) : MoreDetailsController {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                MoreDetailsUiEvent.Search -> searchArtist()
                is MoreDetailsUiEvent.OpenURL -> openURL()
            }
        }

    override fun searchArtist() {
        Thread {
            moreDetailsModel.searchArtist(moreDetailsView.uiState.artistName)
        }.start()
    }

    private fun openURL() {
        moreDetailsView.openExternalLink(moreDetailsView.uiState.artistURL)
    }

}