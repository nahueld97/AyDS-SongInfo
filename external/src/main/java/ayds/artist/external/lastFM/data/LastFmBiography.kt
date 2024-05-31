package ayds.artist.external.lastFM.data

const val LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
data class LastFmBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String
)