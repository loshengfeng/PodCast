package arvin.podcast.api.data

data class PodCastList(
    val podcast: List<PodCast>
)

data class PodCast(
    val artistName: String,
    val artworkUrl100: String,
    val id: String,
    val name: String
)