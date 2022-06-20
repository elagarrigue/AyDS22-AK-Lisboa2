package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.moredetails.model.entities.Source

data class MoreDetailsUiState (
    val artistName: String = "",
    var cardActual: Card? = null,

){
    lateinit var cardsStates: MutableList<CardUiState>

    fun getCard(i : Int) : Card {
        return (cardsStates.firstOrNull{it.card.source == Source.values()[i]}?.card) ?: EmptyCard
    }

    fun initCards(cards: List<Card>) {
        cardsStates= mutableListOf()
        addCards(cards)
        completeWithEmptyCards()
    }

    private fun addCards(cards: List<Card>){
        for(card in cards){
            cardsStates.add(CardUiState(card,false))
        }
    }

    private fun completeWithEmptyCards() {
        while(cardsStates.size<Source.values().size-1) {
            cardsStates.add(CardUiState(EmptyCard, false))
        }
    }

    fun updateEnabledActions(){
        for(i in cardsStates.indices) {
            when (cardsStates[i].card) {
                is CardImpl -> cardsStates[i].enable = true
                is EmptyCard -> cardsStates[i].enable = false
            }
            if(cardsStates[i].card.description.isEmpty())
                cardsStates[i].enable = false
        }
    }

}