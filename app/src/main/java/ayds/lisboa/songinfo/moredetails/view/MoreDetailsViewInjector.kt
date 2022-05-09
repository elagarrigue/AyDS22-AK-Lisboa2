package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.controller.MoreDetailsControllerInjector
import ayds.lisboa.songinfo.moredetails.model.repository.MoreDetailsModelInjector

object MoreDetailsViewInjector {

    val artistInfoFormatter: ArtistInfoFormatter = ArtistInfoFormatterImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsControllerInjector.onViewStarted(moreDetailsView)
    }
}
