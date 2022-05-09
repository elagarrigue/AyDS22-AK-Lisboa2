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
import ayds.lisboa.songinfo.home.view.HomeUiEvent
import ayds.lisboa.songinfo.home.view.HomeUiState
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.repository.MoreDetailsModelInjector
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import ayds.observer.Observable
import ayds.observer.Subject

private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val ARTIST_NAME = "artistName"
private const val LOCAL_DATABASE_PREFIX = "[*]"

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openExternalLink(url: String)
}

class MoreDetailsActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private var artistInfoFormatter: ArtistInfoFormatter = MoreDetailsViewInjector.artistInfoFormatter

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
        initTextPaneArtistInfo()
        imageView = findViewById<View>(R.id.imageView) as ImageView
        openUrlButton = findViewById<View>(R.id.openUrlButton) as Button
    }

    private fun initTextPaneArtistInfo(){
        textPaneArtistBio = findViewById(R.id.textPane2)
    }

    private fun initMoreDetailsModel() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun updateArtistNameState() {
        uiState = uiState.copy(artistName = getArtistName())
    }

    private fun getArtistName() : String =
        intent.getStringExtra(ARTIST_NAME)?:""

    private fun initListener() {
        openUrlButton.setOnClickListener { notifyOpenURLAction() }
    }

    private fun initObserver() {
        moreDetailsModel.artistObservable
            .subscribe { value -> setArtistInfoInView(value) }
    }

    private fun notifySearchAction() {
        onActionSubject.notify(MoreDetailsUiEvent.Search)
    }

    private fun notifyOpenURLAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenURL)
    }

    private fun setArtistInfoInView(artist: Artist) {
        updateArtistURLState(artist)
        setExternalServiceImg()

        setArtistBioInTextPane(artist)
    }

    private fun updateArtistURLState(artist: Artist) {
        uiState = uiState.copy(artistURL = artist.artistURL)
    }

    private fun setExternalServiceImg() {
        runOnUiThread {
            Picasso.get().load(URL_IMAGE).into(imageView)
        }
    }

    private fun setArtistBioInTextPane(artist: Artist){
        runOnUiThread {
            textPaneArtistBio.text = Html.fromHtml(getFinalStringArtistInfo(artist.isLocallyStored,getArtistInfoInHtml(artist)))
        }
    }

    private fun getArtistInfoInHtml(artist: Artist): String =
        artistInfoFormatter.getHtmlArtistInfo(artist)

    //VER SI ESTO TIENE QUE IR EN OTRA CLASE O ACA
    private fun getFinalStringArtistInfo(isLocallyStored: Boolean, artistInfo: String) =
        if (isLocallyStored) {
            "$LOCAL_DATABASE_PREFIX${artistInfo}"
        }
        else {
            artistInfo
        }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}