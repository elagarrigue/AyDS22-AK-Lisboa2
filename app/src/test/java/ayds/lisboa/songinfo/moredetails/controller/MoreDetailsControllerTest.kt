package ayds.lisboa.songinfo.moredetails.controller

import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsUiEvent
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsUiState
import ayds.lisboa.songinfo.moredetails.view.MoreDetailsView
import ayds.observer.Subject
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class MoreDetailsControllerTest {

    private val moreDetailsModel: MoreDetailsModel = mockk(relaxUnitFun = true)

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private val moreDetailsView: MoreDetailsView = mockk(relaxUnitFun = true) {
        every { uiEventObservable } returns onActionSubject
    }

    private val moreDetailsController by lazy {
        MoreDetailsControllerImpl(moreDetailsModel)
    }

    @Before
    fun setup() {
        moreDetailsController.setMoreDetailsView(moreDetailsView)
    }

    @Test
    fun `on search event should search artist`() {
        every { moreDetailsView.uiState } returns MoreDetailsUiState(artistName = "artistName")

        onActionSubject.notify(MoreDetailsUiEvent.Search)

        verify { moreDetailsModel.searchArtist("artistName") }
    }

    @Test
    fun `on open artist url event should open external link`() {
        every { moreDetailsView.uiState } returns MoreDetailsUiState(artistURL = "artistURL")

        onActionSubject.notify(MoreDetailsUiEvent.OpenURL)

        verify { moreDetailsView.openExternalLink("artistURL") }
    }
}