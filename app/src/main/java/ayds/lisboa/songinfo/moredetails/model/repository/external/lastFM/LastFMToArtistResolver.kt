package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

import ayds.lisboa.songinfo.moredetails.model.entities.LastFMArtist
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject


interface LastFMToArtistResolverResolver {
    fun getArtistFromExternalData(serviceData: String?): LastFMArtist?
}

internal class JsonToSongResolver : LastFMToArtistResolverResolver {

    //---------------------------------------------------------------------
    //VER ESO DE QUE AL STRING ARTISTINFO HAY QUE APLICARLE LOS METODOS addLineBreaks Y textToHtml
    //DE LA VISTA
    override fun getArtistFromExternalData(serviceData: String?): LastFMArtist? {
        lateinit var queryArtistInfo: JsonObject
        lateinit var artistBiography: JsonElement
        try {
            queryArtistInfo = Gson().fromJson(serviceData, JsonObject::class.java)
        }
        //PREGUNTA SI PARA OBTENER EL ID, ARTISTNAME Y ARTISTINFO HAY QUE HACERLO COMO EN
        //SPOTIFYTOSONGRESOLVER
    }

}