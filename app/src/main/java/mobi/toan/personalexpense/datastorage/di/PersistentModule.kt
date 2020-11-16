package mobi.toan.personalexpense.datastorage.di

import dagger.Module
import dagger.Provides
import mobi.toan.personalexpense.ExpenseApp
import mobi.toan.personalexpense.datastorage.database.AppDatabase
import mobi.toan.personalexpense.datastorage.database.dao.RecordDao
import mobi.toan.personalexpense.datastorage.repos.RecordRepo
import mobi.toan.personalexpense.datastorage.repos.RecordRepoImpl
import javax.inject.Singleton

@Module
class PersistentModule {
    @Provides
    @Singleton
    fun recordDatabase(): AppDatabase {
        return AppDatabase.getInstance(ExpenseApp.appContext!!)
    }

    @Provides
    @Singleton
    fun recordDao(appDatabase: AppDatabase): RecordDao {
        return appDatabase.recordDao()
    }

    @Provides
    @Singleton
    fun recordRepo(recordDao: RecordDao): RecordRepo {
        return RecordRepoImpl(recordDao)
    }
}