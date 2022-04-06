package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.Song

object DateResolver {
    fun releaseDateText(song: Song): String=
        when (song.releaseDatePrecision) {
            "day" -> precisionDay(song.releaseDate)
            "month" -> precisionMonth(song.releaseDate)
            "year" -> precisionYear(song.releaseDate)
            else -> ""
        }

    private fun precisionDay(date: String): String =
        "${date.split("-")[2]}/${date.split("-")[1]}/${date.split("-")[0]}"

    private fun precisionMonth(date: String): String {
        val months = arrayOf("January","February", "March","April", "May", "June","July","August","September","October","November","December")
        return "${months[date.split("-")[1].toInt()-1]}, ${date.split("-")[0]}"
    }

    private fun precisionYear(date: String): String =
        "$date ${
            if (isALeapYear(date.toInt()))
                "(a leap year)"
            else
                "(not a leap year)"
        }"


    private fun isALeapYear(year: Int) : Boolean =
        ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)
}