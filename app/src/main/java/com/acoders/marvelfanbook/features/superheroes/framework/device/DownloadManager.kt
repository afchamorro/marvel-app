package com.acoders.marvelfanbook.features.superheroes.framework.device

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.acoders.marvelfanbook.features.superheroes.data.MyDownloadManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val DOWNLOAD_FOLDER = "Marvel Fan Book/"

@Singleton
class DownloadManager @Inject constructor(@ApplicationContext private val context: Context): MyDownloadManager {

    override fun download(
        url: String,
        filename: String,
        title: String
    ) {

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(title)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                DOWNLOAD_FOLDER + filename
            )
            .setAllowedOverMetered(true)

        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
    }
}