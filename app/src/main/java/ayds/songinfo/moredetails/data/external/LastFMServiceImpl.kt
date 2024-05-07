package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entity.ArticleEntity

class LastFMServiceImpl : LastFMService {

    private lateinit var lastFMAPI: LastFMAPI
    private lateinit var articleResolver: ArticleResolver

    override fun getArticleByArtistName(artistName: String): ArticleEntity {
        val callResponse = getSongFromService(artistName)
        return articleResolver.getArtistBioFromExternalData(callResponse.body(), artistName)

    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}