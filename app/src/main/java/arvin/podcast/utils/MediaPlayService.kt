package arvin.podcast.utils

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import arvin.podcast.activity.PodCastPlayerActivity
import kotlinx.coroutines.*

class MediaPlayService : Service() {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private val updateUIIntent = Intent()
    private var current = 0
    private var job = Job()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer.setOnPreparedListener {
            updateUIIntent.putExtra(PodCastPlayerActivity.INTENT_TOTAL_TIME, it.duration)
            updateUI(PodCastPlayerActivity.ACTION_UPDATE)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            PodCastPlayerActivity.ACTION_INIT -> {
                mediaPlayer.apply {
                    val url = intent.getStringExtra(PodCastPlayerActivity.INTENT_URL)
                    setDataSource(url)
                    prepareAsync()
                }
            }
            PodCastPlayerActivity.ACTION_PLAY -> {
                mediaPlayer.seekTo(current)
                mediaPlayer.start()
                updateUI(PodCastPlayerActivity.ACTION_PLAY)

                job = CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        delay(500)
                        updateUIIntent.putExtra(PodCastPlayerActivity.INTENT_CURRENT_TIME, mediaPlayer.currentPosition)
                        updateUI(PodCastPlayerActivity.ACTION_UPDATE)
                    }
                }
            }
            PodCastPlayerActivity.ACTION_PAUSE -> {
                mediaPlayer.pause()
                current = mediaPlayer.currentPosition
                updateUI(PodCastPlayerActivity.ACTION_PAUSE)
                job.cancel()
            }
            PodCastPlayerActivity.ACTION_FORWARD -> {
                mediaPlayer.seekTo(mediaPlayer.currentPosition + 30000)
            }
            PodCastPlayerActivity.ACTION_REVERSE -> {
                mediaPlayer.seekTo(mediaPlayer.currentPosition - 30000)
            }
            PodCastPlayerActivity.ACTION_RELEASE -> {
                mediaPlayer.release()
                job.cancel()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun updateUI(action: String) {
        updateUIIntent.action = action
        sendBroadcast(updateUIIntent)
    }
}