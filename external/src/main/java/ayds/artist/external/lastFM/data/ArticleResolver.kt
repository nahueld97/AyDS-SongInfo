package ayds.artist.external.lastFM.data

import ayds.songinfo.moredetails.domain.entity.ArtistBiography
import com.google.gson.Gson
import com.google.gson.JsonObject

interface ArticleResolver {
    fun get(serviceData: String?, artistName: String): ArtistBiography
}

class ArticleResolverImpl : ArticleResolver {
    override fun get(
        serviceData: String?,
        artistName: String
    ): ArtistBiography {
        val gson = Gson()
        val jsonObject = gson.fromJson(serviceData, JsonObject::class.java)

        val artist = jsonObject["artist"].getAsJsonObject()
        val bio = artist["bio"].getAsJsonObject()
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"

        return ArtistBiography(artistName, text, url.asString)
    }
}