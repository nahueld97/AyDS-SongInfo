package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.entity.ArtistBiography

interface LocalService {
    fun getArticle(artistName: String): ArtistBiography?
    fun saveArtist(artistBiography: ArtistBiography)
}

internal class LocalServiceImpl(private val local: ArticleDatabase) : LocalService {

    override fun getArticle(artistName: String): ArtistBiography? {
        val artistData = local.ArticleDao().getArticleByArtistName(artistName)
        return artistData?.let {
            ArtistBiography(
                artistData.artistName,
                artistData.biography,
                artistData.articleUrl
            )
        }
    }

    override fun saveArtist(artistBiography: ArtistBiography) {
        local.ArticleDao().insertArticle(
            ArticleEntity(
                artistBiography.artistName,
                artistBiography.biography,
                artistBiography.articleUrl
            )
        )
    }
}