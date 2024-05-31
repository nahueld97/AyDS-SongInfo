package ayds.songinfo.moredetails.data

import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.artist.external.wikipedia.data.WikipediaTrackService
import ayds.artist.external.lastFM.data.LOGO_URL
import ayds.artist.external.newyorktimes.data.NYT_LOGO_URL
import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource

interface CardProxy {
    fun getCard(artistName : String) : Card?
}

class NewYorkProxy(
    private var service: NYTimesService,
) : CardProxy{
    override fun getCard(artistName: String): Card {
        val data = service.getArtistInfo(artistName) as NYTimesArticle.NYTimesArticleWithData
        return Card(
            data.name?: artistName,
            data.info?: "",
            data.url,
            CardSource.NEW_YORK_TIMES,
            NYT_LOGO_URL
        )
    }

}

class WikipediaProxy(
    private var service: WikipediaTrackService
) : CardProxy{
    override fun getCard(artistName: String): Card? {
        val data = service.getInfo(artistName)
        return data?.let{
            Card(
                artistName,
                data.description,
                data.wikipediaURL,
                CardSource.WIKIPEDIA,
                data.wikipediaLogoURL
            )
        }
    }

}

class LastFMProxy(
    private var service: LastFMService
) : CardProxy{
    override fun getCard(artistName: String): Card {
        val data = service.getArticleByArtistName(artistName)
        return Card(
            data.artistName,
            data.biography,
            data.articleUrl,
            CardSource.LAST_FM,
            LOGO_URL
        )
    }

}