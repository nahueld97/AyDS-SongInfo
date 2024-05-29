package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource

interface LocalService {
    fun getCard(artistName: String): Card?
    fun saveCard(card: Card)
}

internal class LocalServiceImpl(private val local: CardDatabase) : LocalService {

    override fun getCard(artistName: String): Card? {
        val artistData = local.cardDAO().getCardByArtistName(artistName)
        return artistData?.let {
            Card(
                artistData.artistName,
                artistData.biography,
                artistData.articleUrl,
                CardSource.LAST_FM
            )
        }
    }

    override fun saveCard(card: Card) {
        local.cardDAO().insertCard(
            CardEntity(
                card.artistName,
                card.description,
                card.url,
                card.source.ordinal
            )
        )
    }
}