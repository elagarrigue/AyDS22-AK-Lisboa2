package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.moredetails.model.repository.MoreDetailsModelInjector
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView


object MoreDetailsControllerInjector {

    fun onViewStarted(moreDetailsView: MoreDetailsView) =
        MoreDetailsControllerImpl(MoreDetailsModelInjector.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }

}
