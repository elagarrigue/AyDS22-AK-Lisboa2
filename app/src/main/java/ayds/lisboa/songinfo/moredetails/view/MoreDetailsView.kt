package ayds.lisboa.songinfo.moredetails.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.lisboa.songinfo.R
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import ayds.lisboa.songinfo.moredetails.controller.MoreDetailsController
import ayds.lisboa.songinfo.moredetails.controller.MoreDetailsControllerInjector
import ayds.lisboa.songinfo.moredetails.model.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.model.repository.MoreDetailsModelInjector

private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val ARTIST_NAME = "artistName"
private const val LOCAL_DATABASE_PREFIX = "[*]"

class MoreDetailsView : AppCompatActivity() {
    private lateinit var textPaneArtistBio: TextView
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: Button
    private lateinit var moreDetailsModel: MoreDetailsModel
    private lateinit var moreDetailsController: MoreDetailsController
    private lateinit var artist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initViews()
        initMoreDetailsModel()
        initMoreDetailsController()
        getArtistInfo()
        setOnClickerListenerToOpenURLButton()
    }

    private fun initViews() {
        initTextPaneArtistInfo()
        imageView = findViewById<View>(R.id.imageView) as ImageView
        openUrlButton = findViewById<View>(R.id.openUrlButton) as Button
    }

    private fun initMoreDetailsModel() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun initMoreDetailsController() {
        moreDetailsController = MoreDetailsControllerInjector.onViewStarted(this)
    }

    private fun initTextPaneArtistInfo(){
        textPaneArtistBio = findViewById(R.id.textPane2)
    }

    private fun getArtistInfo() {
        Thread {
            artist = moreDetailsModel.searchArtist(getArtistName())
            setArtistInfoInView()
        }.start()
    }

    private fun getArtistName() : String =
        intent.getStringExtra(ARTIST_NAME)?:""

    private fun setArtistInfoInView() {
        setExternalServiceImg()
        setArtistBioInTextPane()
    }

    private fun setExternalServiceImg() {
        runOnUiThread {
            Picasso.get().load(URL_IMAGE).into(imageView)
        }
    }

    private fun setArtistBioInTextPane(){
        runOnUiThread {
            textPaneArtistBio.text = Html.fromHtml(getStringArtistInfo())
        }
    }

    //VER SI ESTO TIENE QUE IR EN OTRA CLASE O ACA
    private fun getStringArtistInfo() =
        if (artist.isLocallyStored) {
            "$LOCAL_DATABASE_PREFIX${artist.artistInfo}"
        }
        else {
            artist.artistInfo
        }

    /*
    private fun notifyOpenURL() {
       onActionSubject.notify(HomeUiEvent.OpenSongUrl)
   }*/

    private fun setOnClickerListenerToOpenURLButton() {
        openUrlButton.setOnClickListener {
            onClickActionOpenURLButton()
        }
    }

    private fun onClickActionOpenURLButton() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artist.artistURL)
        startActivity(intent)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}