package arvin.podcast.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import arvin.podcast.R
import arvin.podcast.api.data.ContentFeed
import arvin.podcast.utils.Action.Companion.ACTION_FORWARD
import arvin.podcast.utils.Action.Companion.ACTION_INIT
import arvin.podcast.utils.Action.Companion.ACTION_PAUSE
import arvin.podcast.utils.Action.Companion.ACTION_PLAY
import arvin.podcast.utils.Action.Companion.ACTION_RELEASE
import arvin.podcast.utils.Action.Companion.ACTION_REVERSE
import arvin.podcast.utils.Action.Companion.ACTION_UPDATE
import arvin.podcast.utils.Data.Companion.INTENT_CURRENT_TIME
import arvin.podcast.utils.Data.Companion.INTENT_DATA
import arvin.podcast.utils.Data.Companion.INTENT_POD_CAST_COVER
import arvin.podcast.utils.Data.Companion.INTENT_TOTAL_TIME
import arvin.podcast.utils.Data.Companion.INTENT_URL
import arvin.podcast.utils.MediaPlayService
import arvin.podcast.utils.TimeUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pod_cast_player.*

class PodCastPlayerActivity : AppCompatActivity() {
    private var mediaSource = String()
    private var intentService: Intent? = null
    private var isPlaying = false
    private var isPrepare = false

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action.toString()) {
                ACTION_PLAY -> {
                    isPlaying = true
                    context?.let { imgPlay.background = ContextCompat.getDrawable(it, R.drawable.ic_pause_circle) }
                }
                ACTION_PAUSE -> {
                    isPlaying = false
                    context?.let { imgPlay.background = ContextCompat.getDrawable(it, R.drawable.ic_play_circle) }
                }
                ACTION_UPDATE -> {
                    isPrepare = true
                    val totalTime = intent?.getIntExtra(INTENT_TOTAL_TIME, 0)
                    val currentTime = intent?.getIntExtra(INTENT_CURRENT_TIME, 0)

                    if (totalTime != 0 && totalTime != null) {
                        beginTime.text = "00:00"
                        endTime.text = TimeUtils.convertTime(totalTime)
                        progressBar.max = totalTime
                    }

                    if (currentTime != 0 && currentTime != null) {
                        beginTime.text = TimeUtils.convertTime(currentTime)
                        progressBar.progress = currentTime
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pod_cast_player)

        initDataAndView()
        setupService()
        setupListener()
        setupBroadcast()
    }

    private fun initDataAndView() {
        val data = intent.getParcelableExtra<ContentFeed>(INTENT_DATA)
        val podCastCover = intent.getStringExtra(INTENT_POD_CAST_COVER)
        mediaSource = data!!.contentUrl

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Glide.with(this).load(podCastCover).into(imgCover)
        podCastTitle.text = data.title
    }

    private fun setupService() {
        intentService = Intent(this@PodCastPlayerActivity, MediaPlayService::class.java)
        intentService?.putExtra(INTENT_URL, mediaSource)
        intentService(ACTION_INIT)
    }

    private fun setupBroadcast() {
        val filter = IntentFilter()
        filter.addAction(ACTION_PLAY)
        filter.addAction(ACTION_PAUSE)
        filter.addAction(ACTION_UPDATE)
        registerReceiver(receiver, filter)
    }

    private fun setupListener() {

        imgPlay.setOnClickListener {
            if (!isPrepare) {
                return@setOnClickListener
            }

            if (isPlaying) {
                intentService(ACTION_PAUSE)
            } else {
                intentService(ACTION_PLAY)
            }
        }

        imgForward.setOnClickListener { intentService(ACTION_FORWARD) }

        imgReverse.setOnClickListener { intentService(ACTION_REVERSE) }
    }

    private fun intentService(action: String) {
        intentService?.action = action
        startService(intentService)
    }

    override fun onDestroy() {
        super.onDestroy()
        intentService(ACTION_RELEASE)
        unregisterReceiver(receiver)
        stopService(intentService)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}