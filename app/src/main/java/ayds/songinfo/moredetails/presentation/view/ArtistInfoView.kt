package ayds.songinfo.moredetails.presentation.view

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import com.squareup.picasso.Picasso

private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
interface ArtistInfoView {
    var presenter : OtherInfoWindow
    fun updateOpenUrlButton(artistBiography: ArtistBiography)
    fun updateLastFMLogo()
    fun updateArticleText(artistBiography: ArtistBiography)
}

class ArtisInfoViewImpl(
    private var artistBiographyTextView: TextView,
    private var lastFMImageView: ImageView,
    private var openUrlButton: Button,
    override var presenter: OtherInfoWindow
) : ArtistInfoView{

    private var artistBiographyHelper: ArtistBiographyHelper = ArtistBiographyHelperImp()

    override fun updateOpenUrlButton(artistBiography: ArtistBiography) {
        openUrlButton.setOnClickListener {
            navigateToUrl(artistBiography.articleUrl)
        }
    }

    override fun updateLastFMLogo() {
        Picasso.get().load(IMAGE_URL).into(lastFMImageView)
    }

    override fun updateArticleText(artistBiography: ArtistBiography) {
        val text = artistBiography.biography.replace("\\n", "\n")
        artistBiographyTextView.text = artistBiographyHelper.getArtistDescriptionText(text,artistBiography.artistName)
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        presenter.startActivity(intent)
    }
}