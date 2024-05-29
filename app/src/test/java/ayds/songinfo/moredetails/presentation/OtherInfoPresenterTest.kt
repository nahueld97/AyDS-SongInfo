package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.presenter.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {
    private val artistInfoRepository: ArtistInfoRepository = mockk()
    private val artistBiographyHelper: ArtistBiographyHelper = mockk()
    private val otherInfoPresenter: OtherInfoPresenter =
        OtherInfoPresenterImpl(artistInfoRepository, artistBiographyHelper)

    @Test
    fun getArtistInfoTest() {
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl")
        every { artistInfoRepository.getArtistInfo("artistName") } returns artistBiography
        every { artistBiographyHelper.getArtistDescriptionText(artistBiography) } returns "description"
        val onUiStateHandler: (ArtistBiographyUiState) -> Unit = mockk(relaxed = true)
        otherInfoPresenter.artistBiographyObservable.subscribe(onUiStateHandler)

        otherInfoPresenter.getArtistInfo("artistName")

        verify { onUiStateHandler(
            ArtistBiographyUiState(
                "artistName",
                "description",
                "articleUrl"
            )
        ) }
    }

}