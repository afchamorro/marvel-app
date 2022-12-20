package com.acoders.marvelfanbook.features.superheroes.data

interface MyDownloadManager {

    fun download(
        url: String,
        filename: String,
        title: String = ""
    )
}