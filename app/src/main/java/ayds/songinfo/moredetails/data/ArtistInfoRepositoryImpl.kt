package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.LastFMService
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.domain.entity.ArticleEntity
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository

class ArtistInfoRepositoryImpl : ArtistInfoRepository {

    private lateinit var local: ArticleDatabase
    private lateinit var external : LastFMService

    override fun getArtistInfo(artistName: String): ArticleEntity {
        val dbArticle = getArticleFromDB(artistName)

        val articleEntity: ArticleEntity

        if (dbArticle != null) {
            articleEntity = dbArticle.markItAsLocal()
        } else {
            articleEntity = getArtistInfoFromAPI(artistName)
            if (articleEntity.biography.isNotEmpty()) {
                saveArtistInfoToDatabase(articleEntity)
            }
        }
        return articleEntity
    }

    private fun getArticleFromDB(artistName: String): ArticleEntity? {
        return local.ArticleDao().getArticleByArtistName(artistName)
    }

    private fun getArtistInfoFromAPI(artistName: String): ArticleEntity {
        return external.getArticleByArtistName(artistName)
    }
    private fun ArticleEntity.markItAsLocal() = copy(biography = "[*]$biography")

    private fun saveArtistInfoToDatabase(artistBiography: ArticleEntity) {
        local.ArticleDao().insertArticle(artistBiography)
    }

}