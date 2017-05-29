package vardansharma.me.picscrambler.ui.game

import android.support.v7.widget.RecyclerView
import com.example.vardansharma.simplesttodoappever.utils.inflate
import com.example.vardansharma.simplesttodoappever.utils.loadImage
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_photos.view.*
import vardansharma.me.picscrambler.R
import vardansharma.me.picscrambler.data.Photo


class PhotoAdapter(val listener: PhotoClickListener) : RecyclerView.Adapter<PhotoAdapter.Companion.PhotoVH>() {
    private var photos: List<Photo>? = null
    private var numImageLoaded = 0

    override fun onBindViewHolder(holder: PhotoVH?, position: Int) {
        holder!!.itemView?.setOnClickListener { listener.onItemClick(position) }
        val photo = photos!![position]
        if (photo.hidePhoto) {
            holder.itemView.img_photos.setImageResource(R.drawable.ic_question)
        } else {
            holder.itemView.img_photos.loadImage(photo.url!!, object : Callback.EmptyCallback() {
                override fun onSuccess() {
                    super.onSuccess()
                    ++numImageLoaded
                    if (numImageLoaded == itemCount) {
                        listener.allImagesLoaded()
                    }
                }
            })
        }

    }

    override fun getItemCount(): Int {
        when (photos) {
            null -> return 0
            else -> return photos!!.size
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup?, viewType: Int): PhotoVH {
        return PhotoVH(parent?.inflate(R.layout.item_photos))
    }

    companion object {
        class PhotoVH(itemView: android.view.View?) : RecyclerView.ViewHolder(itemView)
    }


    fun addPhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    interface PhotoClickListener {
        fun onItemClick(position: Int)
        fun allImagesLoaded()
    }
}