package ayds.artist.external.lastFM.data


class LastFMServiceImpl(
    private var lastFMAPI: LastFMAPI,
    private var lastFMArticleResolver: LastFMArticleResolver
) : LastFMService {

    override fun getArticleByArtistName(artistName: String): LastFmBiography {
        val callResponse = getSongFromService(artistName)
        return lastFMArticleResolver.get(callResponse.body(), artistName)

    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}