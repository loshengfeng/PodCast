package arvin.podcast.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import arvin.podcast.R
import arvin.podcast.adapter.PodCastListAdapter
import arvin.podcast.viewmodel.PodCastViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class PodCastActivity : AppCompatActivity() {

    private val viewModel: PodCastViewModel by inject()

    private val podCastListAdapter = PodCastListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        viewModel.getPodCastList()
        viewModel.podCastsLiveData.observe(this, Observer {
            podCastListAdapter.data = it.podcast
            podCastListAdapter.notifyDataSetChanged()
        })

        podCastListAdapter.setUpListener(object : PodCastListAdapter.OnClickListener {
            override fun onClick() {
                startActivity(Intent(this@PodCastActivity, PodCastDetailActivity::class.java))
            }
        })
    }

    private fun initView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = podCastListAdapter
        }
    }
}
