package mobi.toan.personalexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mobi.toan.personalexpense.datastorage.repos.RecordRepo
import mobi.toan.personalexpense.features.addrecord.RecordViewModel
import mobi.toan.personalexpense.features.home.RecordListViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val recordDataSource: RecordRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordListViewModel::class.java)) {
            return RecordListViewModel(recordDataSource) as T
        }
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(recordDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
