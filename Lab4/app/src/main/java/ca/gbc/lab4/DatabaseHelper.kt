package ca.gbc.lab4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "customers.db"
        private const val DATABASE_VERSION = 1

        private const val CUSTOMER_TABLE = "customers"
        private const val COLUMN_ID = "ID"
        private const val COLUMN_NAME = "NAME"
        private const val COLUMN_AGE = "AGE"
        private const val COLUMN_ACTIVE = "ACTIVE_CUSTOMER"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createStatement = """
            CREATE TABLE $CUSTOMER_TABLE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_AGE INTEGER,
                $COLUMN_ACTIVE INTEGER
            )
        """.trimIndent()

        db?.execSQL(createStatement)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $CUSTOMER_TABLE")
        onCreate(db)
    }

    fun addOne(customer: CustomerModel): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, customer.name)
            put(COLUMN_AGE, customer.age)
            put(COLUMN_ACTIVE, if (customer.isActive) 1 else 0)
        }

        val result = db.insert(
            CUSTOMER_TABLE,
            null,
            values
        )

        db.close()

        return result != -1L
    }

    fun getEveryone(): List<CustomerModel> {

        val customers = mutableListOf<CustomerModel>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $CUSTOMER_TABLE",
            null
        )

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ID)
                )

                val name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME)
                )

                val age = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_AGE)
                )

                val isActive = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ACTIVE)
                ) == 1

                customers.add(
                    CustomerModel(
                        id = id,
                        name = name,
                        age = age,
                        isActive = isActive
                    )
                )

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return customers
    }

    fun deleteOne(customer: CustomerModel): Boolean {

        val db = writableDatabase

        val rowsDeleted = db.delete(
            CUSTOMER_TABLE,
            "$COLUMN_ID = ?",
            arrayOf(customer.id.toString())
        )

        db.close()

        return rowsDeleted > 0
    }
}
