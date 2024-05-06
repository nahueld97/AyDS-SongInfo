package ayds.songinfo.moredetails.presentation.presenter

import android.app.Activity
import android.os.Bundle
import ayds.songinfo.R
import ayds.songinfo.moredetails.domain.entity.ArticleEntity
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.view.ArtistInfoView
import kotlin.concurrent.thread

class OtherInfoWindow : Activity(){

    private lateinit var artistInfoView: ArtistInfoView
    private lateinit var artistInfoRepository: ArtistInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        getArtistInfoAsync()
    }

    private fun getArtistInfoAsync() {
        thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val artistBiography = artistInfoRepository.getArtistInfo()
        updateUi(artistBiography)
    }

    private fun updateUi(artistBiography: ArticleEntity) {
        artistInfoView.updateOpenUrlButton(artistBiography)
        artistInfoView.updateLastFMLogo()
        artistInfoView.updateArticleText(artistBiography)
    }
}