package ayds.lisboa.songinfo.moredetails.fulllogic

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import ayds.lisboa.songinfo.moredetails.fulllogic.DataBase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.ArrayList

class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        fun testDB() {
            var connection = createDBConnection()
            try {
                val rs = createDBStatement(connection).executeQuery("select * from artists")
                while (rs.next()) {
                    println("id = " + rs.getInt("id"))
                    println("artist = " + rs.getString("artist"))
                    println("info = " + rs.getString("info"))
                    println("source = " + rs.getString("source"))
                }
            } catch (e: SQLException) {
                System.err.println(e.message)
            } finally {
                try {
                    connection?.close()
                } catch (e: SQLException) {
                    System.err.println(e)
                }
            }
        }

        private fun createDBConnection(): Connection {
            var connection: Connection? = null
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db")
            return connection
        }

        private fun createDBStatement(connection: Connection): Statement {
            val statement = connection.createStatement()
            statement.queryTimeout = 30
            return statement
        }

        @JvmStatic
        fun saveArtist(dbHelper: DataBase, artist: String?, info: String?) {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("artist", artist)
            values.put("info", info)
            values.put("source", 1)
            db.insert("artists", null, values)
        }

        @JvmStatic
        fun getInfo(dbHelper: DataBase, artist: String): String? {
            val db = dbHelper.readableDatabase
            //specifies which columns from the database you will actually use after this query.
            val projection = arrayOf(
                "id",
                "artist",
                "info"
            )

// Filter results WHERE "title" = 'My Title'
            val selection = "artist  = ?"
            val selectionArgs = arrayOf(artist)

// How you want the results sorted in the resulting Cursor
            val sortOrder = "artist DESC"
            val cursor = db.query(
                "artists",  // The table to query
                projection,  // The array of columns to return (pass null to get all)
                selection,  // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,  // don't group the rows
                null,  // don't filter by row groups
                sortOrder // The sort order
            )
            var artistInfo = ""
            if (cursor.moveToNext()) {
                artistInfo = cursor.getString(
                    cursor.getColumnIndexOrThrow("info")
                )
            }
            cursor.close()
            return artistInfo
        }
    }
}