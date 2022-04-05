package id.wendei.store.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.*
import id.wendei.store.util.DownloadWorker

abstract class BaseViewModel : ViewModel() {

    fun downloadImage(url: String, application: Application) {
        val workManager = WorkManager.getInstance(application)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val downloadWorker = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(Data.Builder().putString("url", url).build())

        workManager.beginUniqueWork(
            "download_image",
            ExistingWorkPolicy.KEEP,
            downloadWorker.build()
        ).enqueue()
    }

}