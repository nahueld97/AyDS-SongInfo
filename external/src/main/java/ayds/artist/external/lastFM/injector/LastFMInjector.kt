package ayds.artist.external.newyorktimes.injector

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.lastFM.data.*
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object LastFmInjector {

    private lateinit var lastFmService: LastFMService

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMToArtistBiographyResolver = LastFMToBiographyResolverImpl()
        lastFmService = LastFMServiceImpl(
            lastFMAPI,
            lastFMToArtistBiographyResolver
        )
    }
}