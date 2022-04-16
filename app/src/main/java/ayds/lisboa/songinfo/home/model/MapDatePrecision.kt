package ayds.lisboa.songinfo.home.model

interface MapDatePrecision {

    fun getDatePrecision(precision: String) : DatePrecision
}

internal class MapDatePrecisionImpl : MapDatePrecision {

    private val precisionDay = "day"
    private val precisionMonth = "month"
    private val precisionYear = "year"

    override fun getDatePrecision(precision: String): DatePrecision =
        when (precision) {
            precisionDay -> DatePrecision.DAY
            precisionMonth -> DatePrecision.MONTH
            precisionYear -> DatePrecision.YEAR
            else -> throw Exception("Date precision invalid.")
        }
}