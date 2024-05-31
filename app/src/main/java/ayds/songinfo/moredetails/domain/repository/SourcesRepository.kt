package ayds.songinfo.moredetails.domain.repository

import ayds.songinfo.moredetails.domain.entity.Card

interface SourcesRepository {
    fun getCards(artistName: String) : List<Card>
}