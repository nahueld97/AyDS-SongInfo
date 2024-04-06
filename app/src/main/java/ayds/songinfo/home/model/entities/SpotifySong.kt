package ayds.songinfo.home.model.entities

import java.text.SimpleDateFormat
import java.util.Locale

sealed class Song {
    data class SpotifySong(
        val id: String,
        val songName: String,
        val artistName: String,
        val albumName: String,
        val releaseDate: String,
        val releaseDatePrecision : String,
        val spotifyUrl: String,
        val imageUrl: String,
        var isLocallyStored: Boolean = false
    ) : Song() {

        val releasedDatePrecised = preciseDate(releaseDate,releaseDatePrecision)

        private fun preciseDate(releaseDate: String, releaseDatePrecision: String): String {
            var fixedDate = ""
            when (releaseDatePrecision) {
                "day" -> {
                    fixedDate=releaseDate.split("-").reversed().toString()
                }
                "month" -> {
                    var date = releaseDate.split("-")
                    fixedDate+=monthName(date[1])+","
                    fixedDate+=date[0]
                }
                "year" -> {
                    val year = releaseDate.toInt()
                    fixedDate += releaseDate
                    fixedDate += when ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                        true -> "(a leap year)"
                        false -> "(not a leap year)"
                    }
                }
            }
            return fixedDate
        }

        private fun monthName(monthNumber: String): String {
            return when (monthNumber){
                "01" -> "January"
                "02" -> "February"
                "03" -> "March"
                "04" -> "April"
                "05" -> "May"
                "06" -> "June"
                "07" -> "July"
                "08" -> "August"
                "09" -> "September"
                "10" -> "October"
                "11" -> "November"
                else -> "December"
            }
        }
    }

    object EmptySong : Song()
}

