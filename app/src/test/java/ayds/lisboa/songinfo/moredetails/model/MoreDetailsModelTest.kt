package ayds.lisboa.songinfo.moredetails.model

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsModelTest {

    private val repository: CardRepository = mockk()

    private val moreDetailsModel: MoreDetailsModel by lazy {
        MoreDetailsModelImpl(repository)
    }

    @Test
    fun `on search artist it should notify the result`() {
        val cards: List<Card> = mockk()
        every { repository.getCardsByName("name") } returns cards
        val cardTester: (List<Card>) -> Unit = mockk(relaxed = true)
        moreDetailsModel.cardObservable.subscribe {
            cardTester(it)
        }

        moreDetailsModel.searchCard("name")

        verify { cardTester(cards) }
    }
}