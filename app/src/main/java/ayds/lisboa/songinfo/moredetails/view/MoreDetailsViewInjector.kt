package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.repository.MoreDetailsModelInjector

object MoreDetailsViewInjector {

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        //HomeControllerInjector.onViewStarted(homeView)
    }
}
