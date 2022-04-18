package ayds.lisboa.songinfo.moredetails.fulllogic

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.lisboa.songinfo.R
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ayds.lisboa.songinfo.moredetails.fulllogic.LastFMAPI
import ayds.lisboa.songinfo.moredetails.fulllogic.DataBase
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonElement
import ayds.lisboa.songinfo.moredetails.fulllogic.OtherInfoWindow
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

    //private JPanel imagePanel;
    // private JLabel posterImageLabel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        open(intent.getStringExtra("artistName"))
    }

    fun getARtistInfo(artistName: String?) {

        Log.e("TAG", "artistName $artistName")
        Thread {
            var artistInfo = getArtistInfoFromDataBase(artistName)
            if (existInDataBase(artistInfo)) {
                artistInfo = "[*]$artistInfo"
            } else { // get from service
                artistInfo = getArtistInfoFromService(artistName)
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

    private fun createRetrofit() =
        Retrofit.Builder()
        .baseUrl("https://ws.audioscrobbler.com/2.0/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun getArtistInfoFromDataBase(artistName: String?) =
        DataBase.getInfo(dataBase, artistName)

    private fun existInDataBase(artistInfo: String?) =
        artistInfo != null

    private fun getArtistInfoFromService(artistName: String?): String? {

        var artistInfo: String = ""

        try {
            val artistBiography = getArtistBiography(artistName)
            val url = artist["url"]
            if (artistBiography == null) {
                artistInfo = "No Results"
            } else {
                artistInfo = artistBiography.asString.replace("\\n", "\n")
                artistInfo = textToHtml(artistInfo, artistName)


                // save to DB  <o/
                DataBase.saveArtist(dataBase, artistName, artistInfo)
            }
            val urlString = url.asString
            findViewById<View>(R.id.openUrlButton).setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urlString)
                startActivity(intent)
            }
        } catch (e1: IOException) {
            Log.e("TAG", "Error $e1")
            e1.printStackTrace()
        }

        return artistInfo
    }

    private fun createLastFMAPI() =
        createRetrofit().create(LastFMAPI::class.java)

    private fun getResponseFromService(artistName: String?) : Response<String> =
        createLastFMAPI().getArtistInfo(artistName).execute()

    private fun getArtistBiography(artistName: String?): JsonElement {

        val artistBiography = getArtist(artistName)["bio"].asJsonObject
        return artistBiography["content"]
    }

    private fun getArtist(artistName: String?): JsonObject {

        val jobj = Gson().fromJson(getResponseFromService(artistName).body(), JsonObject::class.java)
        return jobj["artist"].asJsonObject
    }

    private var dataBase: DataBase? = null
    private fun open(artist: String?) {
        dataBase = DataBase(this)
        DataBase.saveArtist(dataBase, "test", "sarasa")
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "test"))
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "nada"))
        getARtistInfo(artist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        fun textToHtml(text: String, term: String?): String {
            val builder = StringBuilder()
            builder.append("<html><div width=400>")
            builder.append("<font face=\"arial\">")
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace("(?i)" + term!!.toRegex(), "<b>" + term.toUpperCase() + "</b>")
            builder.append(textWithBold)
            builder.append("</font></div></html>")
            return builder.toString()
        }
    }
}