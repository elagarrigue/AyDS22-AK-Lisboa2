package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsUiEvent
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView
import ayds.observer.Observer


interface MoreDetailsController {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
    fun searchCard()
}

internal class MoreDetailsControllerImpl(
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
                MoreDetailsUiEvent.Search -> searchCard()
                MoreDetailsUiEvent.OpenLastFM -> openLastFM()
                MoreDetailsUiEvent.OpenWikipedia -> openWikipedia()
                MoreDetailsUiEvent.OpenNYT -> openNYT()
            }
        }

    override fun searchCard() {
        Thread {
            moreDetailsModel.searchCard(moreDetailsView.uiState.artistName)
        }.start()
    }

    private fun openLastFM() {
        moreDetailsView.cardHandler.navigateToLastFMActivity()
    }

    private fun openWikipedia() {
        moreDetailsView.cardHandler.navigateToWikipediaActivity()
    }

    private fun openNYT() {
        moreDetailsView.cardHandler.navigateToNYTActivity()
    }
}