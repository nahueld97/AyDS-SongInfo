package ayds.songinfo.moredetails.domain.repository

import ayds.songinfo.moredetails.domain.entity.ArticleEntity

interface ArtistInfoRepository {
    fun getArtistInfo(artistName: String) : ArticleEntity
}