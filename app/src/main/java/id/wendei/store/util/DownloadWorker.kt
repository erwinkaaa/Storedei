package id.wendei.store.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import id.wendei.store.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

@Suppress("BlockingMethodInNonBlockingContext")
class DownloadWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val externalFilesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(externalFilesDir, "${System.currentTimeMillis()}.png")
        val url = inputData.getString("url")
        withContext(Dispatchers.IO) {
//            val bitmapDeferred =
//                async { Glide.with(appContext).asBitmap().load(url).submit().get() }
            val bitmap = Glide.with(appContext).asBitmap().load(url).submit().get()
            FileOutputStream(file).use { stream ->
                return@withContext try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.failure()
                }
            }
        }

        val builder = NotificationCompat.Builder(appContext, "store_dei")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Check it in your download folder.")
            .setContentTitle("Download success!")

        NotificationManagerCompat.from(appContext).notify(Random.nextInt(), builder.build())

        return Result.success()
    }

}