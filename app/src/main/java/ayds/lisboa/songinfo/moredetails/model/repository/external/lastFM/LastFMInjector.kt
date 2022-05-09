package ayds.lisboa.songinfo.moredetails.model.repository.external.lastFM

object LastFMInjector {

    val lastFMService: LastFMService = LastFMServiceImpl()

    fun getFormatArtistInfoHelper(name: String) : FormatArtistInfoHelper =
        FormatArtistInfoHelperImpl(name)
}