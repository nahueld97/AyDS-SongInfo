package ayds.songinfo.moredetails.presentation.view

import android.text.Html
import android.text.Spanned
import java.util.Locale


interface ArtistBiographyHelper {
    fun getArtistDescriptionText(text :String, artistName: String): Spanned
}

class ArtistBiographyHelperImp :ArtistBiographyHelper {
    override fun getArtistDescriptionText(text: String, artistName: String): Spanned {
        return Html.fromHtml(textToHtml(text, artistName),
            Html.FROM_HTML_MODE_LEGACY)
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

}