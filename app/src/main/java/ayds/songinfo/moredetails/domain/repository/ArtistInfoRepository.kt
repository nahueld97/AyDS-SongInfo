package ayds.songinfo.moredetails.domain.repository

import ayds.songinfo.moredetails.domain.entity.ArtistBiography

interface ArtistInfoRepository {
    fun getArtistInfo(artistName: String) : ArtistBiography
}