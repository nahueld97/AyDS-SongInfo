package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource
import ayds.songinfo.moredetails.domain.repository.SourcesRepository

class SourcesRepositoryImpl(
    private var local: LocalService,
    private var external: CardBroker
) : SourcesRepository {
    override fun getCards(artistName: String): List<Card> {
        val dbList = local.getCardList(artistName)

        return if(CardSource.entries.size > dbList.size){
            val externalList = external.getCards(artistName)
            externalList.forEach{
                local.saveCard(it)
            }
            externalList
        }else{
            dbList
        }
    }
}