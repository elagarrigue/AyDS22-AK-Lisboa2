package ayds.lisboa.songinfo.moredetails.fulllogic

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.lisboa.songinfo.R
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonElement
import android.content.Intent
import android.net.Uri
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.ImageView
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder

private const val ARTIST_NAME = "artistName"
private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val LOCAL_DATABASE_PREFIX = "[*]"
private const val ARTIST = "artist"
private const val RETROFIT_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
private const val BIOGRAPHY = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val NO_RESULT_MESSAGE = "No Results"

class OtherInfoWindow : AppCompatActivity() {
    private var textPane2: TextView? = null
    private var artistName: String? = null
    private var dataBase: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        open(intent.getStringExtra(ARTIST_NAME))
    }

    private fun open(artist: String?) {
        dataBase = DataBase(this)
        getArtistInfo(artist)
    }

    private fun getArtistInfo(artistName: String?) {
        this.artistName = artistName
        createThreadToGetArtistInfo()
    }

    private fun createThreadToGetArtistInfo() {
        Thread {
            setExternalServiceImg(getArtistInfoFromDataBaseOrService())
        }.start()
    }

    private fun setExternalServiceImg(artistInfo: String) {
        runOnUiThread {
            Picasso.get().load(URL_IMAGE).into(findViewById<View>(R.id.imageView) as ImageView)
            textPane2!!.text = Html.fromHtml(artistInfo)
        }
    }

    private fun getArtistInfoFromDataBaseOrService() : String {
        var artistInfo = getArtistInfoFromDataBase()
        artistInfo = if (existInDataBase(artistInfo)) {
            "$LOCAL_DATABASE_PREFIX$artistInfo"
        } else {
            getArtistInfoFromService()
        }
        return artistInfo
    }

    private fun getArtistInfoFromDataBase() =
        dataBase!!.getInfo(artistName!!)

    private fun existInDataBase(artistInfo: String?) =
        artistInfo != null

    private fun getArtistInfoFromService(): String {
        val queryArtistInfo: JsonObject?
        var artistBiography: JsonElement? = null
        try {
            queryArtistInfo = getQueryBodyOfArtistInfoFromService()
            artistBiography = getArtistBiography(queryArtistInfo)
            setOnClickerListenerToOpenURLButton(queryArtistInfo)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return getStringArtistInfoFromService(artistBiography)
    }

    private fun getQueryBodyOfArtistInfoFromService() =
        Gson().fromJson(getQueryResponseOfArtistInfoFromService().body(), JsonObject::class.java)

    private fun getQueryResponseOfArtistInfoFromService() : Response<String> =
        createLastFMAPI().getArtistInfo(artistName).execute()

    private fun createLastFMAPI() =
        createRetrofit().create(LastFMAPI::class.java)

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    private fun getArtistBiography(jobj: JsonObject): JsonElement =
        getArtist(jobj)[BIOGRAPHY].asJsonObject[CONTENT]

    private fun setOnClickerListenerToOpenURLButton(queryArtistInfo: JsonObject) {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getArtistBiographyURL(queryArtistInfo))
            startActivity(intent)
        }
    }

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)[URL].asString

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].asJsonObject

    private fun getStringArtistInfoFromService(artistBiography: JsonElement?) : String {
        var artistInfo: String
        if (existInService(artistBiography)) {
            artistInfo = addLineBreaks(artistBiography)
            artistInfo = textToHtml(artistInfo)
            saveArtistInDataBase(artistInfo)
        } else {
            artistInfo = NO_RESULT_MESSAGE
        }
        return artistInfo
    }

    private fun existInService(artistBiography: JsonElement?) =
        artistBiography != null

    private fun addLineBreaks(artistBiography: JsonElement?) : String =
        artistBiography!!.asString.replace("\\n", "\n")

    private fun saveArtistInDataBase(artistInfo: String) {
        dataBase!!.saveArtist(artistName, artistInfo)
    }

    private fun textToHtml(text: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + artistName!!.toRegex(), "<b>" + artistName!!.toUpperCase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}