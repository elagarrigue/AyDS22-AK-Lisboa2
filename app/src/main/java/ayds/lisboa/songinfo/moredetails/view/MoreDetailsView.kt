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
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState
    val cards: List<Card>

    fun openExternalLink(url: String)

    fun navigateToLastFMActivity()
    fun navigateToWikipediaActivity()
    fun navigateToNYTActivity()
}

internal class MoreDetailsActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    private var cardFormatter: CardFormatter = MoreDetailsViewInjector.cardFormatter

    private lateinit var textView: TextView
    private lateinit var lastFMButton: Button
    private lateinit var wikipediaButton: Button
    private lateinit var NYTButton: Button

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    override lateinit var cards: List<Card>

    override fun navigateToLastFMActivity() {
        val card = getLastFMCard()
        openCardActivity(card!!)
    }

    override fun navigateToWikipediaActivity() {
        val card = getWikipediaCard()
        openCardActivity(card!!)
    }
    override fun navigateToNYTActivity() {
        val card = getNYTCard()
        openCardActivity(card!!)
    }

    private fun openCardActivity(card: Card) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra(CardActivity.DESCRIPTION_EXTRA, card.description)
        intent.putExtra(CardActivity.INFO_URL_EXTRA, card.infoURL)
        intent.putExtra(CardActivity.SOURCE_EXTRA, card.source)
        intent.putExtra(CardActivity.SOURCE_LOGO_EXTRA, card.sourceLogoUrl)
        startActivity(intent)
    }

    //CAMBIAR PARA QUE BUSQUE EN LA LISTA DE CARDS
    private fun getLastFMCard() : Card? {
        var lastFMCard : Card? = null
        cards.forEach {
            if (it.source == Source.LASTFM) {
                lastFMCard = it
            }
        }
        return lastFMCard
    }

    private fun getWikipediaCard() : Card? {
        var wikipediaCard : Card? = null
        cards.forEach {
            if (it.source == Source.WIKIPEDIA) {
                wikipediaCard = it
            }
        }
        return wikipediaCard
    }

    private fun getNYTCard() : Card? {
        var NYTCard : Card? = null
        cards.forEach {
            if (it.source == Source.NEWYORKTIMES) {
                NYTCard = it
            }
        }
        return NYTCard
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
        onActionSubject.notify(MoreDetailsUiEvent.OpenLastFM)
    }

    private fun notifyWikipediaAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenWikipedia)
    }

    private fun notifyNYTAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenNYT)
    }

    private fun initObserver() {
        moreDetailsModel.cardObservable
            .subscribe { value -> initCardS(value) }
    }

    private fun initCardS(cards: List<Card>) {
        this.cards = cards
    }

    /*private fun getStringArtistInfoFromArtistInfoFormatter(artist: Card): String =
        cardFormatter.getStringArtistInfo(artist)*/

    private fun notifySearchAction() {
        onActionSubject.notify(MoreDetailsUiEvent.Search)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}