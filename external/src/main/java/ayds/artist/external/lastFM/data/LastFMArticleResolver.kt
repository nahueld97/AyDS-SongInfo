package ayds.artist.external.lastFM.data

import com.google.gson.Gson
import com.google.gson.JsonObject

interface LastFMArticleResolver {
    fun get(serviceData: String?, artistName: String): LastFmBiography
}

class LastFMArticleResolverImpl : LastFMArticleResolver {
    override fun get(
        serviceData: String?,
        artistName: String
    ): LastFmBiography {
        val gson = Gson()
        val jsonObject = gson.fromJson(serviceData, JsonObject::class.java)

        val artist = jsonObject["artist"].getAsJsonObject()
        val bio = artist["bio"].getAsJsonObject()
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"

        return LastFmBiography(artistName, text, url.asString)
    }
}