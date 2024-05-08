package ayds.songinfo.moredetails.presentation.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenter
import com.squareup.picasso.Picasso
import java.util.Locale
import kotlin.concurrent.thread


private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"


class OtherInfoWindow : Activity(){

    private lateinit var artistInfoRepository: ArtistInfoRepository
    private lateinit var presenter: OtherInfoPresenter
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView
    private lateinit var artistBiographyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.observer.subscribe{artistBiography = updateUi(ArtistBiography)}
        setContentView(R.layout.activity_other_info)
        initViewProperties()
        getArtistInfoAsync()
    }

    private fun initViewProperties() {
        artistBiographyTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton)
        lastFMImageView = findViewById(R.id.lastFMImageView)
    }

    private fun getArtistName() =
        intent.getStringExtra(OtherInfoWindow.ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun getArtistInfoAsync() {
        thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val artistName = getArtistName()
        val artistBiography = artistInfoRepository.getArtistInfo(artistName)
        updateUi(artistBiography)
    }

    private fun updateUi(artistBiography: ArtistBiography) {
        runOnUiThread {
            updateOpenUrlButton(artistBiography)
            updateLastFMLogo()
            updateArticleText(artistBiography)
        }
    }
    private fun updateOpenUrlButton(artistBiography: ArtistBiography) {
        openUrlButton.setOnClickListener {
            navigateToUrl(artistBiography.articleUrl)
        }
    }
    private fun updateLastFMLogo() {
        Picasso.get().load(IMAGE_URL).into(lastFMImageView)
    }

    private fun updateArticleText(artistBiography: ArtistBiography) {
        val text = artistBiography.biography.replace("\\n", "\n")
        artistBiographyTextView.text = Html.fromHtml(textToHtml(text, artistBiography.artistName),
            Html.FROM_HTML_MODE_LEGACY)
    }
    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}