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
import android.util.Log
import android.view.View
import android.widget.ImageView
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder

class OtherInfoWindow : AppCompatActivity() {
    private var textPane2: TextView? = null
    private var artistName: String? = null

    //private JPanel imagePanel;
    // private JLabel posterImageLabel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        open(intent.getStringExtra("artistName"))
    }

    fun getARtistInfo(artistName: String?) {
        this.artistName = artistName
        Thread {
            var artistInfo = getArtistInfoFromDataBase()
            if (existInDataBase(artistInfo)) {
                artistInfo = "[*]$artistInfo"
            } else { // get from service
                artistInfo = getArtistInfoFromService()
            }
            val imageUrl =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
            Log.e("TAG", "Get Image from $imageUrl")
            val finalText = artistInfo
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView) as ImageView)
                textPane2!!.text = Html.fromHtml(finalText)
            }
        }.start()
    }

    private fun getArtistInfoFromDataBase() =
        DataBase.getInfo(dataBase, artistName)

    private fun existInDataBase(artistInfo: String?) =
        artistInfo != null

    private fun getArtistInfoFromService(): String? {

        var artistInfo: String = ""

        try {
            val queryArtistInfo = getQueryBodyArtistInfoFromService()
            val artistBiography = getArtistBiography(queryArtistInfo)
            if (existInService(artistBiography)) {
                artistInfo = addLineBreaks(artistBiography)
                artistInfo = textToHtml(artistInfo)
                saveArtistInDataBase(artistInfo)
            } else {
                artistInfo = "No Results"
            }
            setOnClickerListenerToOpenURLButton(queryArtistInfo)
        } catch (e1: IOException) {
            Log.e("TAG", "Error $e1")
            e1.printStackTrace()
        }

        return artistInfo
    }

    private fun setOnClickerListenerToOpenURLButton(queryArtistInfo: JsonObject) {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getArtistBiographyURL(queryArtistInfo))
            startActivity(intent)
        }
    }

    private fun getQueryBodyArtistInfoFromService() =
        Gson().fromJson(getQueryResponseArtistInfoFromService().body(), JsonObject::class.java)

    private fun getQueryResponseArtistInfoFromService() : Response<String> =
        createLastFMAPI().getArtistInfo(artistName).execute()

    private fun createLastFMAPI() =
        createRetrofit().create(LastFMAPI::class.java)

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    private fun getArtistBiography(jobj: JsonObject): JsonElement {

        val artistBiography = getArtist(jobj)["bio"].asJsonObject
        return artistBiography["content"]
    }

    private fun getArtistBiographyURL(jobj: JsonObject): String =
        getArtist(jobj)["url"].asString

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj["artist"].asJsonObject

    private fun existInService(artistBiography: JsonElement?) =
        artistBiography != null

    private fun addLineBreaks(artistBiography: JsonElement) : String =
        artistBiography.asString.replace("\\n", "\n")

    private fun saveArtistInDataBase(artistInfo: String) {
        DataBase.saveArtist(dataBase, artistName, artistInfo)
    }

    private var dataBase: DataBase? = null
    private fun open(artist: String?) {
        dataBase = DataBase(this)
        DataBase.saveArtist(dataBase, "test", "sarasa")
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "test"))
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "nada"))
        getARtistInfo(artist)
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