package ayds.songinfo.moredetails.presentation.presenter

import android.app.Activity
import android.os.Bundle
import ayds.songinfo.R
import ayds.songinfo.moredetails.data.ArtistInfoRepositoryImpl
import ayds.songinfo.moredetails.domain.entity.ArticleEntity
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.view.ArtisInfoViewImpl
import ayds.songinfo.moredetails.presentation.view.ArtistInfoView
import kotlin.concurrent.thread

class OtherInfoWindow : Activity(){

    private lateinit var artistInfoView: ArtistInfoView
    private lateinit var artistInfoRepository: ArtistInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        init()
        getArtistInfoAsync()
    }

    private fun init() {
        artistInfoView = ArtisInfoViewImpl(
            findViewById(R.id.textPane1),
            findViewById(R.id.lastFMImageView),
            findViewById(R.id.openUrlButton),
            this)
        artistInfoRepository = ArtistInfoRepositoryImpl()
    }

    private fun getArtistInfoAsync() {
        thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val artistBiography = artistInfoRepository.getArtistInfo(ARTIST_NAME_EXTRA)
        updateUi(artistBiography)
    }

    private fun updateUi(artistBiography: ArticleEntity) {
        runOnUiThread {
            artistInfoView.updateOpenUrlButton(artistBiography)
            artistInfoView.updateLastFMLogo()
            artistInfoView.updateArticleText(artistBiography)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}