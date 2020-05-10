package arvin.podcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import arvin.podcast.R
import arvin.podcast.api.data.ContentFeed

class PodCastDetailAdapter : RecyclerView.Adapter<PodCastDetailAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null

    var data = listOf<ContentFeed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cast_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedData = data[position]
        holder.bind(feedData)
        holder.itemView.setOnClickListener {
            listener?.onClick(feedData)
        }
    }

    fun setUpListener(listener: OnClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(data: ContentFeed) {
            title.text = data.title
        }
    }

    interface OnClickListener {
        fun onClick(data: ContentFeed)
    }
}