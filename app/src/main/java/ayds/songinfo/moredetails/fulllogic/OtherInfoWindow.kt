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

class OtherInfoWindow : Activity() {
    private var artistBiographyTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistBiographyTextView = findViewById(R.id.textPane1)
        open(intent.getStringExtra(ARTIST_NAME_EXTRA))
    }

    private fun getArtistInfo(artistName: String?) {

        // create
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)
        Log.e("TAG", "artistName $artistName")
        Thread {
            val artistInfo = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            var biography = ""
            if (artistInfo != null) { // exists in db
                biography = "[*]" + artistInfo.biography
                val articleUrl = artistInfo.articleUrl
                configureOpenUrlButton(articleUrl)
            } else {
                try {
                    val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
                    Log.e("TAG", "JSON " + callResponse.body())
                    val gson = Gson()
                    val jsonObject: JsonObject = gson.fromJson(callResponse.body(), JsonObject::class.java)
                    val artistObject = jsonObject["artist"].getAsJsonObject()
                    val bioObject = artistObject["bio"].getAsJsonObject()
                    val extract = bioObject["content"]
                    val url = artistObject["url"]
                    if (extract == null) {
                        biography = "No Results"
                    } else {
                        biography = extract.asString.replace("\\n", "\n")
                        biography = formatBiographyText(biography, artistName)

                        val textToSave = biography
                        Thread {
                            dataBase!!.ArticleDao().insertArticle(
                                ArticleEntity(
                                    artistName, textToSave, url.asString
                                )
                            )
                        }
                            .start()
                    }
                    val urlString = url.asString
                    configureOpenUrlButton(urlString)
                } catch (e: IOException) {
                    Log.e("TAG", "Error $e")
                    e.printStackTrace()
                }
            }
            val imageUrl =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
            Log.e("TAG", "Get Image from $imageUrl")
            val finalBiography = biography
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView1) as ImageView)
                artistBiographyTextView!!.text = Html.fromHtml(finalBiography,Html.FROM_HTML_MODE_LEGACY)
            }
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
        getArtistInfo(artist)
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
