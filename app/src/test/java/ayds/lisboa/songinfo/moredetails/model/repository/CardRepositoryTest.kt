package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.repository.external.Broker
import ayds.lisboa.songinfo.moredetails.model.repository.local.card.CardLocalStorage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class CardRepositoryTest {

    private val cardLocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val broker: Broker = mockk(relaxUnitFun = true)

    private val cardLastFM = CardImpl(
        "name",
        "description",
        "url",
        Source.LASTFM,
        "logoUrl",
        false
    )
    private val cardNYT = CardImpl(
        "name",
        "description",
        "url",
        Source.NEWYORKTIMES,
        "logoUrl",
        false
    )
    private val cardWkp = CardImpl(
        "name",
        "description",
        "url",
        Source.WIKIPEDIA,
        "logoUrl",
        false
    )

    private val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(cardLocalStorage, broker)
    }

    @Test
    fun `given existing artist by name should return artist and mark it as local`() {
        var cards: List<Card> = mutableListOf(cardLastFM,cardNYT,cardWkp)
        every { cardLocalStorage.getCardsByName("name") } returns cards

        val result = cardRepository.getCardsByName("name")

        assertEquals(cards, result)
        for (card in cards) {
            assertTrue(card.isLocallyStored)
        }
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        var cards: List<Card> = mutableListOf(cardLastFM,cardNYT,cardWkp)
        every { cardLocalStorage.getCardsByName("name") } returns emptyList()
        every { broker.getCards("name") } returns cards

        val result = cardRepository.getCardsByName("name")

        assertEquals(cards, result)

        for (card in cards) {
            assertFalse(card.isLocallyStored)
        }
        for (card in cards) {
            verify { cardLocalStorage.saveCard(card) }
        }
    }

    @Test
    fun `given non existing artist by name should return empty list`() {
        every { cardLocalStorage.getCardsByName("name") } returns emptyList()
        every { broker.getCards("name") } returns emptyList()

        val result = cardRepository.getCardsByName("name")
        val emptyList: List<Card> = mutableListOf()

        assertEquals(emptyList, result)
    }

}