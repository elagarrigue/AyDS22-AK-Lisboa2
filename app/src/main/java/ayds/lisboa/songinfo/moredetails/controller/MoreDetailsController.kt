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
                MoreDetailsUiEvent.OpenSource -> openSource()
            }
        }

    override fun searchCard() {
        Thread {
            moreDetailsModel.searchCard(moreDetailsView.uiState.artistName)
        }.start()
    }

    private fun openSource() {
        moreDetailsView.openCardActivity(moreDetailsView.uiState.cardActual!!)
    }
}