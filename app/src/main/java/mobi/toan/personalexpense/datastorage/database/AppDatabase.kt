package mobi.toan.personalexpense.datastorage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mobi.toan.personalexpense.datastorage.database.dao.RecordDao
import mobi.toan.personalexpense.datastorage.database.entity.Record

@Database(entities = [Record::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val DB_NAME = "expense.db"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DB_NAME
            ).build()
    }
}
