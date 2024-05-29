package ayds.songinfo.moredetails.data

import ayds.artist.external.lastFM.data.LastFMService
import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.entity.CardSource
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class ArtistInfoRepositoryTest {

    private var local: LocalService = mockk(relaxUnitFun = true)
    private var external: LastFMService = mockk(relaxUnitFun = true)

    private val artistInfoRepository: ArtistInfoRepository =
        ArtistInfoRepositoryImpl(local, external)

    @Test
    fun getArtistInfoLocalTest() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM, false)

        every { local.getCard("artistName") } returns card

        val result = artistInfoRepository.getCard("artistName")

        Assert.assertEquals(card, result)
        Assert.assertEquals(card.isLocallyStored, true)
    }

    @Test
    fun getArtistInfoExternalNullBiographyTest() {
        val card = Card("artist", "biography", "url", CardSource.LAST_FM,false)

        every { local.getCard("artistName") } returns null
        every { external.getArticleByArtistName("artistName") } returns card
        every{ local.saveCard(card)} returns Unit

        val result = artistInfoRepository.getCard("artistName")

        Assert.assertEquals(card, result)
        Assert.assertEquals(card.isLocallyStored, false)
        verify { local.saveCard(card) }
    }

@Test
fun getArtistInfoExternalTest(){
    val card = Card("artistName", "", "url", CardSource.LAST_FM,false)

    every { local.getCard("artistName") } returns null
    every { external.getArticleByArtistName("artistName") } returns card

    val result = artistInfoRepository.getCard("artistName")

    Assert.assertEquals(card, result)
    Assert.assertEquals(card.isLocallyStored, false)
}
}