package arvin.podcast.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arvin.podcast.R
import arvin.podcast.activity.PodCastPlayerActivity.Companion.INTENT_DATA
import arvin.podcast.activity.PodCastPlayerActivity.Companion.INTENT_POD_CAST_COVER
import arvin.podcast.adapter.PodCastDetailAdapter
import arvin.podcast.api.data.Collection
import arvin.podcast.api.data.ContentFeed
import arvin.podcast.viewmodel.PodCastDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pod_cast_detail.*
import org.koin.android.ext.android.inject

class PodCastDetailActivity : AppCompatActivity() {

    private val viewModel: PodCastDetailViewModel by inject()
    private val podCastDetailAdapter = PodCastDetailAdapter()

    private var podCastCover = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pod_cast_detail)

        initView()
        viewModel.getPodCastDetail()
        viewModel.podCastDetailLiveData.observe(this, Observer {
            podCastCover = it.collection.artworkUrl100
            updateView(it.collection)
        })

        podCastDetailAdapter.setUpListener(object : PodCastDetailAdapter.OnClickListener {
            override fun onClick(data: ContentFeed) {
                val intent = Intent(this@PodCastDetailActivity, PodCastPlayerActivity::class.java)
                intent.putExtra(INTENT_DATA, data)
                intent.putExtra(INTENT_POD_CAST_COVER, podCastCover)
                startActivity(intent)
            }
        })
    }

    private fun initView() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = podCastDetailAdapter
        }
    }

    private fun updateView(data: Collection) {
        Glide.with(this).load(podCastCover).into(imgPodCast)
        artistName.text = data.artistName
        podCastName.text = data.collectionName
        podCastDetailAdapter.data = data.contentFeed
        podCastDetailAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}