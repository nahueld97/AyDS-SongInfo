package ayds.songinfo.moredetails.data

import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.lastFM.data.LastFmBiography
import ayds.artist.external.lastFM.data.LOGO_URL
import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository

class ArtistInfoRepositoryImpl(
    private var local: LocalService,
    private var external: LastFMService
) : ArtistInfoRepository {


    override fun getCard(artistName: String): Card {
        val dbCard = local.getCard(artistName)

        val card: Card

        if (dbCard != null) {
            card = dbCard.apply { markItAsLocal() }
        } else {
            card = external.getArticleByArtistName(artistName).toCard()
            if (card.description.isNotEmpty()) {
                local.saveCard(card)
            }
        }
        return card
    }

    private fun LastFmBiography.toCard() =
        Card(artistName, biography, articleUrl, CardSource.LAST_FM, LOGO_URL)

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}