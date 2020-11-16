package mobi.toan.personalexpense.di

import dagger.Component
import mobi.toan.personalexpense.datastorage.di.PersistentModule
import mobi.toan.personalexpense.features.addrecord.AddRecordActivity
import mobi.toan.personalexpense.features.home.MainActivity
import javax.inject.Singleton

@Component(
    modules = [PersistentModule::class]
)
@Singleton
interface ApplicationComponent {
    fun inject(activity: AddRecordActivity)

    fun inject(activity: MainActivity)
}

