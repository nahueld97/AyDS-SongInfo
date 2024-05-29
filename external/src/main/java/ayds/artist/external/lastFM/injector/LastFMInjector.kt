package ayds.artist.external.newyorktimes.injector

import ayds.artist.external.lastFM.data.LastFMArticleResolverImpl
import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.lastFM.data.LastFMServiceImpl
import ayds.artist.external.lastFM.data.LastFMAPI
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val API_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {

    lateinit var lastFmService: LastFMService

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val articleResolver = LastFMArticleResolverImpl()
        lastFmService = LastFMServiceImpl(
            lastFMAPI,
            articleResolver
        )
    }


}