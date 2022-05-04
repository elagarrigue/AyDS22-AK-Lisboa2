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
    private lateinit var artist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initViews()
        initMoreDetailsModel()
        getArtistInfo()
        setArtistInfoInView()
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

    private fun getArtistName() : String =
        intent.getStringExtra(ARTIST_NAME)?:""


    private fun initTextPaneArtistInfo(){
        textPaneArtistBio = findViewById(R.id.textPane2)
    }

    private fun getArtistInfo() {
        Thread {
            artist = moreDetailsModel.searchArtist(getArtistName())
        }.start()
    }

    private fun setArtistInfoInView() {
        setExternalServiceImg()
        setArtistBioInTextPane()
    }

    private fun setExternalServiceImg() {
        runOnUiThread {
            Picasso.get().load(URL_IMAGE).into(imageView)
        }
    }

    private fun setArtistBioInTextPane() {
        runOnUiThread {
            textPaneArtistBio.text = Html.fromHtml(getStringArtistInfo())
        }
    }

    private fun getStringArtistInfo() =
        if (artist.isLocallyStored) {
            "$LOCAL_DATABASE_PREFIX${artist.artistInfo}"
        }
        else {
            artist.artistInfo
        }


    /*private fun setOnClickerListenerToOpenURLButton(queryArtistInfo: JsonObject) {
        openUrlButton.setOnClickListener {
            onClickActionOpenURLButton(queryArtistInfo)
        }
    }

    private fun onClickActionOpenURLButton(queryArtistInfo: JsonObject){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(getArtistBiographyURL(queryArtistInfo))
        startActivity(intent)
    }*/

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}