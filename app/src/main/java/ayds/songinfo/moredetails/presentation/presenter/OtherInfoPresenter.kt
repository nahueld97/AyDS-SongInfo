package ayds.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository


interface OtherInfoPresenter{
    var observer : Observable<ArtistBiography>

    fun getArtistInfo(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private var artistInfoRepository: ArtistInfoRepository
) : OtherInfoPresenter {

    private var artistBiographySubject = Subject<ArtistBiography>()
    override var observer: Observable<ArtistBiography> = artistBiographySubject

    override fun getArtistInfo(artistName: String){
        val artistBiography = artistInfoRepository.getArtistInfo(artistName)
        artistBiographySubject.notify(artistBiography)
    }


}