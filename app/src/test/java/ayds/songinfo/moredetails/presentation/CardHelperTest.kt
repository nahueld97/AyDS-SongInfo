package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.entity.Card
import org.junit.Assert
import org.junit.Test

class CardHelperTest {
    private val cardHelper: CardHelper = CardHelperImp()

    @Test
    fun getArtistDescriptionTextWithoutEnterTest() {
        val card = Card("artistName", "biography", "articleUrl")

        val biography = cardHelper.getDescription(card)

        val result = "<html><div width=400><font face=\"arial\">biography</font></div></html>"
        Assert.assertEquals(biography, result)
    }
    @Test
    fun getArtistDescriptionTextWithEnterTest(){
        val card = Card("artistName", "biography\\n", "articleUrl")

        val biography = cardHelper.getDescription(card)

        val result = "<html><div width=400><font face=\"arial\">biography<br></font></div></html>"
        Assert.assertEquals(biography, result)
    }

    @Test
    fun getArtistDescriptionTextWithNTest(){
        val card = Card("artistName", "biography'n", "articleUrl")

        val biography = cardHelper.getDescription(card)

        val result = "<html><div width=400><font face=\"arial\">biography n</font></div></html>"
        Assert.assertEquals(biography, result)
    }

    @Test
    fun getArtistDescriptionTextWithArtisNameTest(){
        val card = Card("artistName", "artistName", "articleUrl")

        val biography = cardHelper.getDescription(card)

        val result = "<html><div width=400><font face=\"arial\"><b>ARTISTNAME</b></font></div></html>"
        Assert.assertEquals(biography, result)
    }
}