package ayds.songinfo.moredetails.presentation.view

import ayds.songinfo.moredetails.domain.entity.ArticleEntity

interface ArtistInfoView {
    fun updateOpenUrlButton(artistBiography: ArticleEntity)
    fun updateLastFMLogo()
    fun updateArticleText(artistBiography: ArticleEntity)
}