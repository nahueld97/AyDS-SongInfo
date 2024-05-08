package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.ExternalService
import ayds.songinfo.moredetails.data.local.LocalService
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository

class ArtistInfoRepositoryImpl(
    private var local: LocalService,
    private var external: ExternalService
) : ArtistInfoRepository {


    override fun getArtistInfo(artistName: String): ArtistBiography {
        val dbArticle = local.getArticle(artistName)

        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.markItAsLocal()
        } else {
            artistBiography = external.getArticleByArtistName(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                local.saveArtist(artistBiography)
            }
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() = copy(biography = "[*]$biography")


}