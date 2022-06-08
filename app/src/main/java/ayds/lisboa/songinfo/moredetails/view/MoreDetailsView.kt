package ayds.lisboa.songinfo.moredetails.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ayds.lisboa.songinfo.R
import android.view.View
import android.widget.Button
import android.widget.TextView
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModelInjector
import ayds.lisboa.songinfo.moredetails.model.Source
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.moredetails.model.entities.CardImpl
import ayds.lisboa.songinfo.moredetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openExternalLink(url: String)

    fun openCardActivity(card: Card)
}

internal class MoreDetailsActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    private lateinit var textView: TextView
    private lateinit var lastFMButton: Button
    private lateinit var wikipediaButton: Button
    private lateinit var NYTButton: Button

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    override fun openCardActivity(card: Card) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra(CardActivity.DESCRIPTION_EXTRA, card.description)
        intent.putExtra(CardActivity.INFO_URL_EXTRA, card.infoURL)
        intent.putExtra(CardActivity.SOURCE_EXTRA, card.source.toString())
        intent.putExtra(CardActivity.SOURCE_LOGO_EXTRA, card.sourceLogoUrl)
        intent.putExtra(CardActivity.IS_LOCALLY_STORED_EXTRA, card.isLocallyStored)
        intent.putExtra(CardActivity.ARTIST_NAME_EXTRA, card.artistName)
        startActivity(intent)
    }

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViews()
        initMoreDetailsModel()
        updateArtistNameState()
        initListener()
        initObserver()
        notifySearchAction()
    }

    private fun initViews() {
        textView = findViewById(R.id.textView)
        lastFMButton = findViewById<View>(R.id.buttonLastFM) as Button
        wikipediaButton = findViewById<View>(R.id.buttonWikipedia) as Button
        NYTButton = findViewById<View>(R.id.buttonNYT) as Button
        lastFMButton.isEnabled= false
        wikipediaButton.isEnabled= false
        NYTButton.isEnabled= false
    }

    private fun initMoreDetailsModel() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun updateArtistNameState() {
        uiState = uiState.copy(artistName = getArtistName())
    }

    private fun getArtistName() : String =
        intent.getStringExtra(ARTIST_NAME_EXTRA)?:""

    private fun initListener() {
        lastFMButton.setOnClickListener { notifyLastFMAction() }
        wikipediaButton.setOnClickListener { notifyWikipediaAction() }
        NYTButton.setOnClickListener { notifyNYTAction() }
    }

    private fun notifyLastFMAction() {
        uiState.cardActual = getLastFMCard()
        onActionSubject.notify(MoreDetailsUiEvent.OpenSource)
    }

    private fun notifyWikipediaAction() {
        uiState.cardActual = getWikipediaCard()
        onActionSubject.notify(MoreDetailsUiEvent.OpenSource)
    }

    private fun notifyNYTAction() {
        uiState.cardActual = getNYTCard()
        onActionSubject.notify(MoreDetailsUiEvent.OpenSource)
    }


    private fun getLastFMCard() : Card {
        return uiState.cards.firstOrNull{it.source == Source.LASTFM} ?: EmptyCard
    }

    private fun getWikipediaCard() : Card {
        return uiState.cards.firstOrNull{it.source == Source.WIKIPEDIA} ?: EmptyCard
    }

    private fun getNYTCard() : Card {
        return uiState.cards.firstOrNull{it.source == Source.NEWYORKTIMES} ?: EmptyCard
    }

    private fun initObserver() {
        moreDetailsModel.cardObservable
            .subscribe { value -> initProperties(value) }
    }

    private fun initProperties(cards: List<Card>) {
        uiState.cards = cards
        updateButtonsStates()
        setButtonsEnable()
    }

    private fun updateButtonsStates(){
        for(i in uiState.cards.indices) {
            when (uiState.cards[i]) {
                is CardImpl -> uiState.actionsEnabled[i] = true
                is EmptyCard -> uiState.actionsEnabled[i] = false
            }
            if(uiState.cards[i].description.isEmpty())
                uiState.actionsEnabled[i] = false
        }
    }

    private fun setButtonsEnable(){
        runOnUiThread {
            lastFMButton.isEnabled=uiState.actionsEnabled[0]
            NYTButton.isEnabled=uiState.actionsEnabled[1]
            wikipediaButton.isEnabled=uiState.actionsEnabled[2]
        }
    }
    private fun notifySearchAction() {
        onActionSubject.notify(MoreDetailsUiEvent.Search)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}