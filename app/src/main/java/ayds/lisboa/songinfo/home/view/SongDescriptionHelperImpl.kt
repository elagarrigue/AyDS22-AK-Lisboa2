package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.EmptySong
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.entities.SpotifySong



interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
    fun setDateResolver(dateResolver: DateResolver)
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {

    private lateinit var dateResolver: DateResolver

    override fun setDateResolver(dateResolver: DateResolver){
        this.dateResolver = dateResolver
    }

    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${dateResolver.releaseDateText(song)}"
            else -> "Song not found"
        }
    }


}



