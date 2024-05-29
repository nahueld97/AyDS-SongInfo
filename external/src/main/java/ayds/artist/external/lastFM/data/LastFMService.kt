package ayds.artist.external.lastFM.data

import ayds.songinfo.moredetails.domain.entity.ArtistBiography

interface LastFMService {
    fun getArticleByArtistName(artistName: String):ArtistBiography
}
