package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

private const val IMAGE_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

class OtherInfoWindow : Activity() {
    private var artistBiographyTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistBiographyTextView = findViewById(R.id.textPane1)
        open(intent.getStringExtra(ARTIST_NAME_EXTRA))
    }

    private fun showArtistInfo(artistName: String?) {
        Log.e("TAG", "artistName $artistName")
        Thread {
            val artistInfo = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            val biography = if (artistInfo != null) {
                configureOpenUrlButton(artistInfo.articleUrl)
                "[*]" + artistInfo.biography
            } else {
                configureOpenUrlButton(getArtistURLFromApi(artistName))
                getArtistInfoFromAPI(artistName)
            }

            Log.e("TAG", "Get Image from $IMAGE_URL")
            runOnUiThread {
                Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView1) as ImageView)
                artistBiographyTextView!!.text = Html.fromHtml(
                    biography,
                    Html.FROM_HTML_MODE_LEGACY
                )
            }
        }.start()
    }
    private fun getArtistURLFromApi(artistName: String): String {
        val jsonObject = getJsonArtistInfo(artistName)
        val artistObject = jsonObject["artist"].getAsJsonObject()
        return artistObject["url"].asString
    }

    private fun getJsonArtistInfo(artistName: String) : JsonObject {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        Log.e("TAG", "JSON " + callResponse.body())
        val gson = Gson()
        return gson.fromJson(callResponse.body(), JsonObject::class.java)
    }

    private fun getArtistInfoFromAPI(artistName: String): String {
        try {
            val jsonObject = getJsonArtistInfo(artistName)
            val artistObject = jsonObject["artist"].getAsJsonObject()
            val bioObject = artistObject["bio"].getAsJsonObject()
            val extract = bioObject["content"]
            return if (extract == null) {
                "No Results"
            } else {
                val biography = extract.asString.replace("\\n", "\n")
                val formattedBiography = formatBiographyText(biography, artistName)
                saveArtistInfoToDatabase(artistName, formattedBiography, artistObject["url"].asString)
                formattedBiography
            }
        } catch (e: IOException) {
            Log.e("TAG", "Error $e")
            e.printStackTrace()
            return "Error: ${e.message}"
        }
    }

    private fun saveArtistInfoToDatabase(artistName: String, biography: String, url: String) {
        Thread {
            dataBase!!.ArticleDao().insertArticle(ArticleEntity(artistName, biography, url))
        }.start()
    }

    private fun configureOpenUrlButton(articleUrl: String) {
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private var dataBase: ArticleDatabase? = null
    private fun open(artist: String?) {
        dataBase =
            databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
        Thread {
            dataBase!!.ArticleDao().insertArticle(ArticleEntity("test", "sarasa", ""))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("test"))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("nada"))
        }.start()
        showArtistInfo(artist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        fun formatBiographyText(text: String, term: String?): String {
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
    }
}
