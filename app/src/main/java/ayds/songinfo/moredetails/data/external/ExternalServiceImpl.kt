package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entity.ArtistBiography

class ExternalServiceImpl(
    private var lastFMAPI: LastFMAPI,
    private var articleResolver: ArticleResolver
) : ExternalService {

    override fun getArticleByArtistName(artistName: String): ArtistBiography {
        val callResponse = getSongFromService(artistName)
        return articleResolver.get(callResponse.body(), artistName)

    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}