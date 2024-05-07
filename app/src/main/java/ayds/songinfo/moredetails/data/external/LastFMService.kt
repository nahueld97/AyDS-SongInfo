package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entity.ArticleEntity

interface LastFMService {
    fun getArticleByArtistName(artistName: String):ArticleEntity
}
