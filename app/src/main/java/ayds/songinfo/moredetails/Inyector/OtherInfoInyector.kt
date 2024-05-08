package ayds.songinfo.moredetails.Inyector

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.ArtistInfoRepositoryImpl
import ayds.songinfo.moredetails.data.external.ArticleResolverImpl
import ayds.songinfo.moredetails.data.external.ExternalServiceImpl
import ayds.songinfo.moredetails.data.external.LastFMAPI
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.LocalServiceImpl
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val DATABASE_NAME = "article-database"
private const val API_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

class OtherInfoInyector {

    private fun initGraph(context : Context) {

        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val articleDatabase = Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).build()

        val localService = LocalServiceImpl(articleDatabase)
        val resolver = ArticleResolverImpl()
        val externalService = ExternalServiceImpl(lastFMAPI, resolver)


        val artistInfoRepository = ArtistInfoRepositoryImpl(localService, externalService)

        val presenter = OtherInfoPresenterImpl(artistInfoRepository)
    }
}