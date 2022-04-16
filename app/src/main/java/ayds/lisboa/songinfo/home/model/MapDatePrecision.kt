package ayds.lisboa.songinfo.home.model

interface MapDatePrecision {

    fun getDatePrecision(precision: String) : DatePrecision
}

internal class MapDatePrecisionImpl : MapDatePrecision {

    override fun getDatePrecision(precision: String): DatePrecision =
        when (precision) {
            "day" -> DatePrecision.DAY
            "month" -> DatePrecision.MONTH
            "year" -> DatePrecision.YEAR
            else -> throw Exception("Date precision invalid.")
        }
}