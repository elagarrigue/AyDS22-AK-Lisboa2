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
    private var cargando = false

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
            cargando=true
            moreDetailsModel.searchCard(moreDetailsView.uiState.artistName)
            cargando=false
        }.start()
    }

    private fun openLastFM() {
        if(!cargando)
            moreDetailsView.cardHandler.navigateToLastFMActivity()
    }

    private fun openWikipedia() {
        if(!cargando)
            moreDetailsView.cardHandler.navigateToWikipediaActivity()
    }

    private fun openNYT() {
        if(!cargando)
            moreDetailsView.cardHandler.navigateToNYTActivity()
    }
}