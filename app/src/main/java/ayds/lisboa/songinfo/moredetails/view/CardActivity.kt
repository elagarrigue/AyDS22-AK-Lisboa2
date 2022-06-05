package ayds.lisboa.songinfo.moredetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import com.squareup.picasso.Picasso

class CardActivity : AppCompatActivity() {
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    private lateinit var textPaneArtistBio: TextView
    private lateinit var imageView: ImageView
    private lateinit var textPaneSource: TextView
    private lateinit var openURLButton: Button

    //VER LO DE APLICARLE AL FORMATO A LA DESCRIPCION
    private var cardFormatter: CardFormatter = MoreDetailsViewInjector.cardFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        initViews()
        initListener()
        updateViews()
    }

    private fun initViews() {
        textPaneArtistBio = findViewById(R.id.textPane2)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        textPaneSource = findViewById(R.id.textSource)
        openURLButton = findViewById<View>(R.id.openUrlButton) as Button
    }

    private fun initListener() {
        openURLButton.setOnClickListener { openURL() }
    }

    private fun openURL() {
        navigationUtils.openExternalUrl(this, intent.getStringExtra(INFO_URL_EXTRA)?:"")
    }

    private fun updateViews() {
        textPaneArtistBio.text = intent.getStringExtra(DESCRIPTION_EXTRA)?:""
        val source = "Source: ${intent.getStringExtra(SOURCE_EXTRA)?:""}"
        textPaneSource.text = source
        Picasso.get().load(intent.getStringExtra(SOURCE_LOGO_EXTRA)?:"").into(imageView)
    }


    companion object {
        const val DESCRIPTION_EXTRA = "description"
        const val SOURCE_EXTRA = "source"
        const val INFO_URL_EXTRA = "info_url"
        const val SOURCE_LOGO_EXTRA = "source_logo"

    }
}