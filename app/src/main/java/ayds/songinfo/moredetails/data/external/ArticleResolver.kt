package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.entity.ArticleEntity
import com.google.gson.Gson
import com.google.gson.JsonObject

interface ArticleResolver{
    fun getArtistBioFromExternalData(serviceData: String?, artistName: String): ArticleEntity
}

class ArticleResolverImpl : ArticleResolver{
    override fun getArtistBioFromExternalData(
        serviceData: String?,
        artistName: String
    ): ArticleEntity {
        val gson = Gson()
        val jsonObject = gson.fromJson(serviceData, JsonObject::class.java)

        val artist = jsonObject["artist"].getAsJsonObject()
        val bio = artist["bio"].getAsJsonObject()
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"

        return ArticleEntity(artistName, text, url.asString)
    }
}