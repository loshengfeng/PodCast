package arvin.podcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import arvin.podcast.R
import arvin.podcast.api.data.PodCast
import com.bumptech.glide.Glide

class PodCastListAdapter : RecyclerView.Adapter<PodCastListAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null

    var data = listOf<PodCast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_casts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val podCastData = data[position]
        holder.bind(podCastData)
        holder.itemView.setOnClickListener {
            listener?.onClick()
        }
    }

    fun setUpListener(listener: OnClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgPodCast: ImageView = itemView.findViewById(R.id.imgPodCast)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)
        private val podCastName: TextView = itemView.findViewById(R.id.podCastName)

        fun bind(data: PodCast) {
            Glide.with(itemView.context).load(data.artworkUrl100).into(imgPodCast)
            artistName.text = data.artistName
            podCastName.text = data.name
        }
    }

    interface OnClickListener {
        fun onClick()
    }
}