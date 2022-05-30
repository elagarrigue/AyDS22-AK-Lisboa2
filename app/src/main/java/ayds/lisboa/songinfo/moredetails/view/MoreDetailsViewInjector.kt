package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.controller.MoreDetailsControllerInjector
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModelInjector

object MoreDetailsViewInjector {

    val cardFormatter: CardFormatter = CardFormatterImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsControllerInjector.onViewStarted(moreDetailsView)
    }
}
