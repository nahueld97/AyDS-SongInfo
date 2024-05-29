package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.presenter.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {
    private val artistInfoRepository: ArtistInfoRepository = mockk()
    private val cardHelper: CardHelper = mockk()
    private val otherInfoPresenter: OtherInfoPresenter =
        OtherInfoPresenterImpl(artistInfoRepository, cardHelper)

    @Test
    fun getArtistInfoTest() {
        val card = Card("artistName", "biography", "articleUrl")
        every { artistInfoRepository.getCard("artistName") } returns card
        every { cardHelper.getDescription(card) } returns "description"
        val onUiStateHandler: (CardUiState) -> Unit = mockk(relaxed = true)
        otherInfoPresenter.cardObservable.subscribe(onUiStateHandler)

        otherInfoPresenter.updateCard("artistName")

        verify { onUiStateHandler(
            CardUiState(
                "artistName",
                "description",
                "articleUrl"
            )
        ) }
    }

}