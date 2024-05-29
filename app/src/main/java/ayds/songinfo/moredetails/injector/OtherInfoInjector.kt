package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.newyorktimes.injector.LastFMInjector
import ayds.songinfo.moredetails.data.ArtistInfoRepositoryImpl
import ayds.songinfo.moredetails.data.local.CardDatabase
import ayds.songinfo.moredetails.data.local.LocalServiceImpl
import ayds.songinfo.moredetails.presentation.CardHelperImp
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenterImpl

private const val DATABASE_NAME = "article-database"


object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {
        LastFMInjector.init()

        val cardDatabase = Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            DATABASE_NAME
        ).build()

        val localService = LocalServiceImpl(cardDatabase)

        val artistInfoRepository = ArtistInfoRepositoryImpl(localService, LastFMInjector.lastFmService)

        val artistBiographyHelper = CardHelperImp()

        presenter = OtherInfoPresenterImpl(artistInfoRepository, artistBiographyHelper)
    }
}