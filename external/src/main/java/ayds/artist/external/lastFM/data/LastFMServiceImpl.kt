package ayds.artist.external.lastFM.data

import java.io.IOException


class LastFMServiceImpl(
    private var lastFMAPI: LastFMAPI,
    private var lastFMArticleResolver: LastFMToBiographyResolver
) : LastFMService {

    override fun getArticleByArtistName(artistName: String): LastFmBiography {
        var lastFmBiography = LastFmBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            lastFmBiography = lastFMArticleResolver.map(callResponse.body(), artistName)
        }catch (e1: IOException) {
            e1.printStackTrace()
        }
        return lastFmBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}