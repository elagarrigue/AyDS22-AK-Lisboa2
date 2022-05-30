package ayds.lisboa.songinfo.moredetails.view

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.lisboa.songinfo.R
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModelInjector
import ayds.lisboa.songinfo.moredetails.model.entities.Card
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openExternalLink(url: String)
}

internal class MoreDetailsActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private var cardFormatter: CardFormatter = MoreDetailsViewInjector.cardFormatter

    private lateinit var textPaneArtistBio: TextView
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: Button

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

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
        textPaneArtistBio = findViewById(R.id.textPane2)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        openUrlButton = findViewById<View>(R.id.openUrlButton) as Button
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
        openUrlButton.setOnClickListener { notifyOpenURLAction() }
    }

    private fun notifyOpenURLAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenURL)
    }

    private fun initObserver() {
        moreDetailsModel.artistObservable
            .subscribe { value -> setArtistInfoInView(value) }
    }

    private fun setArtistInfoInView(card: Card) {
        updateArtistURLState(card)
        setExternalServiceImg()
        setArtistBioInTextPane(card)
    }

    private fun updateArtistURLState(card: Card) {
        uiState = uiState.copy(artistURL = card.infoURL)
    }

    private fun setExternalServiceImg() {
        runOnUiThread {
            Picasso.get().load(URL_IMAGE).into(imageView)
        }
    }

    private fun setArtistBioInTextPane(artist: Card) {
        runOnUiThread {
            textPaneArtistBio.text = Html.fromHtml(getStringArtistInfoFromArtistInfoFormatter(artist))
        }
    }

    private fun getStringArtistInfoFromArtistInfoFormatter(artist: Card): String =
        cardFormatter.getStringArtistInfo(artist)

    private fun notifySearchAction() {
        onActionSubject.notify(MoreDetailsUiEvent.Search)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}