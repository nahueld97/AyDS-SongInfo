package ayds.songinfo.moredetails.domain.entity

data class Card(
    val artistName: String,
    val description: String,
    val url: String,
    val source: CardSource,
    var isLocallyStored: Boolean = false
)

enum class CardSource {
    LAST_FM,
    WIKIPEDIA,
    NEW_YORK_TIMES
}