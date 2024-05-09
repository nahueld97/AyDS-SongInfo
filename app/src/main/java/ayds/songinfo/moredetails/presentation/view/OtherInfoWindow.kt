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
import ayds.songinfo.moredetails.injector.OtherInfoInjector
import com.squareup.picasso.Picasso
import ayds.songinfo.moredetails.presentation.presenter.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.ArtistBiographyUiState

class OtherInfoWindow : Activity(){

    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView
    private lateinit var artistBiographyTextView: TextView

    private lateinit var presenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViewProperties()
        initPresenter()

        observePresenter()
        getArtistInfoAsync()
    }

    private fun initPresenter() {
        OtherInfoInjector.initGraph(this)
        presenter = OtherInfoInjector.presenter
    }

    private fun observePresenter(){
        presenter.artistBiographyObservable.subscribe { artistBiography  ->
            updateUi(artistBiography)
        }
    }

    private fun initViewProperties() {
        artistBiographyTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton)
        lastFMImageView = findViewById(R.id.lastFMImageView)
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun getArtistInfoAsync() {
        Thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val artistName = getArtistName()
        presenter.getArtistInfo(artistName)
    }

    private fun updateUi(uiState: ArtistBiographyUiState) {
        runOnUiThread {
            updateOpenUrlButton(uiState.articleUrl)
            updateLastFMLogo(uiState.imageUrl)
            updateArticleText(uiState.infoHtml)
        }
    }
    private fun updateOpenUrlButton(url: String) {
        openUrlButton.setOnClickListener {
            navigateToUrl(url)
        }
    }
    private fun updateLastFMLogo(url: String) {
        Picasso.get().load(url).into(lastFMImageView)
    }

    private fun updateArticleText(infoHtml: String) {
        artistBiographyTextView.text = Html.fromHtml(infoHtml,
            Html.FROM_HTML_MODE_LEGACY)
    }
    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}