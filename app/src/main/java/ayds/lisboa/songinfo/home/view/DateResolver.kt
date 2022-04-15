package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.DatePrecision

interface DateResolver {

    fun releaseDateText(song: Song): String

}

internal class DateResolverImpl: DateResolver {

	private val months = arrayOf("January","February", "March","April", "May", "June","July","August","September","October","November","December")

    override fun releaseDateText(song: Song): String=
        when (song.releaseDatePrecision) {
            DatePrecision.DAY -> precisionDay(song.releaseDate)
            DatePrecision.MONTH -> precisionMonth(song.releaseDate)
            DatePrecision.YEAR -> precisionYear(song.releaseDate)
        }

    private fun precisionDay(date: String): String {
        val dateSplit = date.split("-")
        val day = dateSplit[2]
        val month = dateSplit[1]
        val year = dateSplit[0]
        return "$day/$month/$year"
    }

    private fun precisionMonth(date: String): String {
        val dateSplit = date.split("-")
        val month = months[dateSplit[1].toInt()-1]
        val year = dateSplit[0]
        return "$month, $year"
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