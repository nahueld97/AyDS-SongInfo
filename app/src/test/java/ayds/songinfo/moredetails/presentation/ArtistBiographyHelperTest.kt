package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import org.junit.Assert
import org.junit.Test

class ArtistBiographyHelperTest {
    private val artistBiographyHelper: ArtistBiographyHelper = ArtistBiographyHelperImp()

    @Test
    fun getArtistDescriptionTextWithoutEnterTest() {
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl")

        val biography = artistBiographyHelper.getArtistDescriptionText(artistBiography)

        val result = "<html><div width=400><font face=\"arial\">biography</font></div></html>"
        Assert.assertEquals(biography, result)
    }
    @Test
    fun getArtistDescriptionTextWithEnterTest(){
        val artistBiography = ArtistBiography("artistName", "biography\\n", "articleUrl")

        val biography = artistBiographyHelper.getArtistDescriptionText(artistBiography)

        val result = "<html><div width=400><font face=\"arial\">biography<br></font></div></html>"
        Assert.assertEquals(biography, result)
    }

    @Test
    fun getArtistDescriptionTextWithNTest(){
        val artistBiography = ArtistBiography("artistName", "biography'n", "articleUrl")

        val biography = artistBiographyHelper.getArtistDescriptionText(artistBiography)

        val result = "<html><div width=400><font face=\"arial\">biography n</font></div></html>"
        Assert.assertEquals(biography, result)
    }

    @Test
    fun getArtistDescriptionTextWithArtisNameTest(){
        val artistBiography = ArtistBiography("artistName", "artistName", "articleUrl")

        val biography = artistBiographyHelper.getArtistDescriptionText(artistBiography)

        val result = "<html><div width=400><font face=\"arial\"><b>ARTISTNAME</b></font></div></html>"
        Assert.assertEquals(biography, result)
    }
}