package ayds.lisboa.songinfo.moredetails.model

import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface  MoreDetailsModel {

    val cardObservable: Observable<Card>

    fun searchCard(name: String)
}

internal class MoreDetailsModelImpl(private val repository: CardRepository) : MoreDetailsModel {

    override val cardObservable = Subject<Card>()

    override fun searchCard(name: String) {
        repository.getCardByName(name)?.let {
            cardObservable.notify(it)
        }
    }
}

