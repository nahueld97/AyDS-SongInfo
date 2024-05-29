package ayds.songinfo.moredetails.domain.repository

import ayds.songinfo.moredetails.domain.entity.Card

interface ArtistInfoRepository {
    fun getCard(artistName: String) : Card
}