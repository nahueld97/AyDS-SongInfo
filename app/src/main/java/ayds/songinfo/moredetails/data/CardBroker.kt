package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.domain.entity.Card
import java.util.LinkedList

interface CardBroker {
    fun getCards(artistName: String): List<Card>
}

class CardBrokerImpl(
    private val proxyList : List<CardProxy>
) : CardBroker{
    override fun getCards(artistName: String): List<Card> {
        val cardList = LinkedList<Card>()
        proxyList.forEach{ proxy ->
            proxy.getCard(artistName).let {
                cardList.addLast(it)
            }
        }
        return cardList
    }

}
