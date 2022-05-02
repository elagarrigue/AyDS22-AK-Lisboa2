package ayds.lisboa.songinfo.moredetails.model.repository.local.lastFM.sqldb

const val DATABASE_NAME = "dictionary.db"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val ID_COLUMN = "id"
const val ARTIST_TABLE_NAME = "artists"

const val createArtistTableQuery: String =
    "create table $ARTIST_TABLE_NAME (" +
            "$ID_COLUMN string PRIMARY KEY, " +
            "$ARTIST_COLUMN string, " +
            "$INFO_COLUMN string )"