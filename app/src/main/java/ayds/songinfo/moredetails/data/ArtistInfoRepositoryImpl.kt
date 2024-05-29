package ayds.songinfo.moredetails.data

import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.lastFM.data.LastFmBiography
import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository

class ArtistInfoRepositoryImpl(
    private var local: LocalService,
    private var external: LastFMService
) : ArtistInfoRepository {


    override fun getArtistInfo(artistName: String): ArtistBiography {
        val dbArticle = local.getArticle(artistName)

        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.apply { markItAsLocal() }
        } else {
            artistBiography = external.getArticleByArtistName(artistName).toArtistBiography()
            if (artistBiography.biography.isNotEmpty()) {
                local.saveArtist(artistBiography)
            }
        }
        return artistBiography
    }

    private fun LastFmBiography.toArtistBiography() =
        ArtistBiography(artistName, biography, articleUrl)

    private fun ArtistBiography.markItAsLocal() {
        isLocallyStored = true
    }
}