package uz.alijonov.foodexpress.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.alijonov.foodexpress.R

class GlideUtils {
    companion object {
        fun loadImage(imageView: ImageView, url: String?) {
            if (url == null) {
                imageView.setImageDrawable(null)
                return
            }
            Glide.with(imageView).load(url)
                .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.photo_placeholder))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("JW ERR", url + " | " + e?.localizedMessage)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)
        }
    }
}