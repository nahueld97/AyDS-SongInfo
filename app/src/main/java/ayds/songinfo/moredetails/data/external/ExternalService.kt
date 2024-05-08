package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entity.ArtistBiography

interface ExternalService {
    fun getArticleByArtistName(artistName: String):ArtistBiography
}
