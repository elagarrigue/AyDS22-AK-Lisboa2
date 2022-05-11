package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModelInjector
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsControllerInjector {

    fun onViewStarted(moreDetailsView: MoreDetailsView): MoreDetailsController =
        MoreDetailsControllerImpl(MoreDetailsModelInjector.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }

}
