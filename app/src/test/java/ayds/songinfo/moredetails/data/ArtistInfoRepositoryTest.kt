package ayds.songinfo.moredetails.data

import ayds.artist.external.lastFM.data.LastFMService
import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ArtistInfoRepositoryTest {

    private var local: LocalService = mockk(relaxUnitFun = true)
    private var external: LastFMService = mockk(relaxUnitFun = true)

    private val artistInfoRepository: ArtistInfoRepository =
        ArtistInfoRepositoryImpl(local, external)

    @Test
    fun getArtistInfoLocalTest() {
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl")

        every { local.getArticle("artistName") } returns artistBiography

        val result = artistInfoRepository.getArtistInfo("artistName")

        Assert.assertEquals(artistBiography, result)
        Assert.assertEquals(artistBiography.isLocallyStored, true)
    }

    @Test
    fun getArtistInfoExternalNullBiographyTest() {
        val artistBiography = ArtistBiography("artistName", "", "articleUrl")

        every { local.getArticle("artistName") } returns null
        every { external.getArticleByArtistName("artistName") } returns artistBiography

        val result = artistInfoRepository.getArtistInfo("artistName")

        Assert.assertEquals(artistBiography, result)
        Assert.assertEquals(artistBiography.isLocallyStored, false)
    }

@Test
fun getArtistInfoExternalTest(){
    val artistBiography = ArtistBiography("artistName", "biography", "articleUrl")

    every { local.getArticle("artistName") } returns null
    every { external.getArticleByArtistName("artistName") } returns artistBiography

    val result = artistInfoRepository.getArtistInfo("artistName")

    Assert.assertEquals(artistBiography, result)
    Assert.assertEquals(artistBiography.isLocallyStored, false)
}
}