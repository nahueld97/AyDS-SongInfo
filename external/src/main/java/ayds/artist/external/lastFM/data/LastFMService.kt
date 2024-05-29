package ayds.artist.external.lastFM.data

interface LastFMService {
    fun getArticleByArtistName(artistName: String):LastFmBiography
}
