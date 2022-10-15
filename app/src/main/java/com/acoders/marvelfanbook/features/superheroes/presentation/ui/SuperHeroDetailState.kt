package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.acoders.marvelfanbook.features.superheroes.data.MyDownloadManager
import com.acoders.marvelfanbook.features.superheroes.framework.device.DownloadManager
import com.acoders.marvelfanbook.features.superheroes.framework.device.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//TODO TD: TRY INJECT INSTEAD THIS BUILD FUNCTION
fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    storagePermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ),
    downloadManager: MyDownloadManager = DownloadManager(requireContext())
) = SuperHeroDetailState(scope, storagePermissionRequester, downloadManager)

class SuperHeroDetailState(
    private val scope: CoroutineScope,
    private val storagePermissionRequester: PermissionRequester,
    private val downloadManager: MyDownloadManager
) {

    fun requestStoragePermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = storagePermissionRequester.request()
            afterRequest(result)
        }
    }

    fun downloadSuperHeroCover(imageUrl: String, title: String) {
        scope.launch {
            val fileName = "$title${System.currentTimeMillis()}.jpg"
            downloadManager.download(url = imageUrl, title = title, filename = fileName)
        }
    }
}