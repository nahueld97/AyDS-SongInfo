package ayds.songinfo.moredetails.domain.entity

data class Card(
    val artistName: String,
    val description: String,
    val url: String,
    val source: CardSource,
    val logoURL : String,
    var isLocallyStored: Boolean = false //TODO borrar luego del nuevo repositiry
)

enum class CardSource {
    LAST_FM,
    WIKIPEDIA,
    NEW_YORK_TIMES
}