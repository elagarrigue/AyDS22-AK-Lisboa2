package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

const val DATABASE_NAME = "dictionary.db"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val ID_COLUMN = "id"
const val URL_COLUMN = "url"
const val ARTIST_TABLE_NAME = "artists"

const val createArtistTableQuery: String =
    "create table $ARTIST_TABLE_NAME (" +
            "$ID_COLUMN integer primary key autoincrement, " +
            "$ARTIST_COLUMN string, " +
            "$INFO_COLUMN string " +
            "$URL_COLUMN string )"