package ayds.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.ArtistBiographyHelper
import ayds.songinfo.moredetails.presentation.ArtistBiographyUiState


interface OtherInfoPresenter{
    val artistBiographyObservable : Observable<ArtistBiographyUiState>

    fun getArtistInfo(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private val artistInfoRepository: ArtistInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyHelper
) : OtherInfoPresenter {

    override val artistBiographyObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String){
        val artistBiography = artistInfoRepository.getArtistInfo(artistName)
        val uiState = artistBiography.toUiState()
        artistBiographyObservable.notify(uiState)
    }

    private fun ArtistBiography.toUiState() = ArtistBiographyUiState(
        artistName,
        artistBiographyDescriptionHelper.getArtistDescriptionText(this),
        articleUrl
    )


}