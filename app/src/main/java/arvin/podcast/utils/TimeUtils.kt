package arvin.podcast.utils

import java.util.concurrent.TimeUnit

object TimeUtils {
    fun convertTime(millisecond: Int): String {
        val millsConvertSeconds = TimeUnit.MILLISECONDS.toSeconds(millisecond.toLong())
        val minutes = String.format("%02d", millsConvertSeconds / 60)
        val second = String.format("%02d", millsConvertSeconds % 60)
        return "$minutes:$second"
    }
}