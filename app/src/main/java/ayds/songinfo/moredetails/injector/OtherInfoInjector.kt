package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.newyorktimes.injector.LastFMInjector
import ayds.songinfo.moredetails.data.ArtistInfoRepositoryImpl
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.LocalServiceImpl
import ayds.songinfo.moredetails.presentation.ArtistBiographyHelperImp
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenterImpl

private const val DATABASE_NAME = "article-database"


object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {
        LastFMInjector.init()

        val articleDatabase = Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).build()

        val localService = LocalServiceImpl(articleDatabase)

        val artistInfoRepository = ArtistInfoRepositoryImpl(localService, LastFMInjector.lastFmService)

        val artistBiographyHelper = ArtistBiographyHelperImp()

        presenter = OtherInfoPresenterImpl(artistInfoRepository, artistBiographyHelper)
    }
}