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
    private var searching = false

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
            searching=true
            moreDetailsModel.searchCard(moreDetailsView.uiState.artistName)
            searching=false
        }.start()
    }

    private fun openLastFM() {
        if(!searching)
            moreDetailsView.cardHandler.navigateToLastFMActivity()
    }

    private fun openWikipedia() {
        if(!searching)
            moreDetailsView.cardHandler.navigateToWikipediaActivity()
    }

    private fun openNYT() {
        if(!searching)
            moreDetailsView.cardHandler.navigateToNYTActivity()
    }
}