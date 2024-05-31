package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource
import java.util.LinkedList

interface LocalService {
    fun getCard(artistName: String): Card?
    fun saveCard(card: Card)

    fun getCardList(artistName: String): List<Card>
}

internal class LocalServiceImpl(private val local: CardDatabase) : LocalService {

    override fun getCard(artistName: String): Card? {
        val artistData = local.cardDAO().getCardByArtistName(artistName)
        return artistData?.let {
            Card(
                artistData.artistName,
                artistData.biography,
                artistData.articleUrl,
                CardSource.entries[artistData.source],
                artistData.logoURL
            )
        }
    }

    override fun saveCard(card: Card) {
        local.cardDAO().insertCard(
            CardEntity(
                card.artistName,
                card.description,
                card.url,
                card.source.ordinal,
                card.logoURL
            )
        )
    }

    override fun getCardList(artistName: String): List<Card> {
        val list = local.cardDAO().getAllCardsByArtistName(artistName)

        val cardList = LinkedList<Card>()
        val enum = CardSource.entries

        list.forEach{
            cardList.addLast(
                Card(
                    it.artistName,
                    it.biography,
                    it.articleUrl,
                    enum[it.source],
                    it.logoURL
                )
            )
        }
        return cardList
    }

}