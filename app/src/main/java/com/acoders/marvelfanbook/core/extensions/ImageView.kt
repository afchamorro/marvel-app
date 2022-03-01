package com.acoders.marvelfanbook.core.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.io.File


/**
 * GLIDE EXTENSIONS
 */
fun ImageView.load(url: String?) {
    prepare().load(url)
}

fun ImageView.load(@RawRes @DrawableRes resourceId: Int) {
    prepare().load(resourceId)
}

fun ImageView.load(drawable: Drawable) {
    prepare().load(drawable)
}

fun ImageView.load(uri: Uri?) {
    prepare().load(uri)
}

fun ImageView.load(bitmap: Bitmap?) {
    prepare().load(bitmap)
}

fun ImageView.load(file: File?) {
    prepare().load(file)
}

fun ImageView.prepare(): ImageGlideBuilder = ImageGlideBuilder(this)


interface ImageLoader {
    fun rounded(radius: Int = 20000): ImageLoader
    fun fadeIn(): ImageLoader
    fun resize(size: Int): ImageLoader
    fun resize(width: Int, height: Int): ImageLoader
    fun placeholder(@DrawableRes drawable: Int): ImageLoader
    fun fallback(@DrawableRes drawable: Int): ImageLoader
    fun error(@DrawableRes drawable: Int): ImageLoader
    fun centerCrop(): ImageLoader
    fun circleCrop(): ImageLoader
    fun fitCenter(): ImageLoader
    fun load(any: Any?)
}


class ImageGlideBuilder(private val imageView: ImageView) : ImageLoader {

    // scale types
    private var fitCenter: Boolean = false
    private var centerCrop: Boolean = false
    private var centerInside: Boolean = false
    private var circleCrop: Boolean = false

    // transformations
    private var roundedRadius: Int = -1
    private var height: Int = -1
    private var width: Int = -1

    // transitions
    private var fadeIn: Boolean = false

    // preload & postload
    private var placeholder: Int = -1
    private var fallback: Int = -1
    private var error: Int = -1


    override fun rounded(radius: Int): ImageLoader {
        this.roundedRadius = radius
        return this
    }

    override fun fadeIn(): ImageLoader {
        this.fadeIn = true
        return this
    }

    override fun resize(size: Int): ImageLoader {
        this.width = size
        this.height = size
        return this
    }

    override fun resize(width: Int, height: Int): ImageLoader {
        this.width = width
        this.height = height
        return this
    }

    override fun error(@DrawableRes drawable: Int): ImageLoader {
        this.error = drawable
        return this
    }

    override fun placeholder(@DrawableRes drawable: Int): ImageLoader {
        this.placeholder = drawable
        return this
    }

    override fun fallback(@DrawableRes drawable: Int): ImageLoader {
        this.fallback = drawable
        return this
    }

    override fun centerCrop(): ImageLoader {
        this.centerCrop = true
        return this
    }

    override fun circleCrop(): ImageLoader {
        this.circleCrop = true
        return this
    }

    override fun fitCenter(): ImageLoader {
        this.fitCenter = true
        return this
    }

    override fun load(any: Any?) {

        // image & request options
        var glide = Glide.with(imageView.context)
            .load(any)
            .apply(getRequestOptions())

        // transformations
        val transformations = getTransformations()
        if (transformations.isNotEmpty()) {
            glide = glide.transform(MultiTransformation(transformations))
        }

        // transitions
        val transition = getTransition()
        if (transition != null) {
            glide = glide.transition(transition)
        }

        // load configuration into image
        glide.into(imageView)
    }

    private fun getRequestOptions(): RequestOptions {
        return RequestOptions()
            .placeholder(placeholder)
            .fallback(fallback)
            .error(error)
            .override(width, height)
    }

    private fun getTransformations(): MutableList<Transformation<Bitmap>> {

        // prepare transformations
        val transformations = mutableListOf<Transformation<Bitmap>>()

        // scale type
        if (centerInside) {
            transformations.add(CenterInside())
        }
        if (centerCrop) {
            transformations.add(CenterCrop())
        }
        if (fitCenter) {
            transformations.add(FitCenter())
        }
        if (circleCrop) {
            transformations.add(CircleCrop())
        }
        // rounded
        if (roundedRadius >= 1) {
            transformations.add(RoundedCorners(roundedRadius))
        }

        return transformations
    }

    private fun getTransition(): DrawableTransitionOptions? {
        return if (fadeIn) {
            DrawableTransitionOptions.withCrossFade(100)
        } else {
            null
        }
    }

}

