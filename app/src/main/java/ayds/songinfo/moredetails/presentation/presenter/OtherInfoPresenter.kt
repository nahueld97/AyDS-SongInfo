package ayds.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.entity.Card
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.CardHelper
import ayds.songinfo.moredetails.presentation.CardUiState


interface OtherInfoPresenter{
    val cardObservable : Observable<CardUiState>

    fun updateCard(artistName: String)
}

internal class OtherInfoPresenterImpl(
    private val repository: ArtistInfoRepository,
    private val cardHelper: CardHelper
) : OtherInfoPresenter {

    override val cardObservable = Subject<CardUiState>()

    override fun updateCard(artistName: String){
        val card = repository.getCard(artistName)
        val uiState = card.toUiState()
        cardObservable.notify(uiState)
    }

    private fun Card.toUiState() = CardUiState(
        artistName,
        cardHelper.getDescription(this),
        url
    )


}