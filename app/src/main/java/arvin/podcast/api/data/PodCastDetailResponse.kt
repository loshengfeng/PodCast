package arvin.podcast.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PodCastDetail(
    var collection: Collection
)

data class Collection(
    val artistId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl600: String,
    val collectionId: Int,
    val collectionName: String,
    val contentFeed: List<ContentFeed>,
    val country: String,
    val genreIds: String,
    val genres: String,
    val releaseDate: String
)

@Parcelize
data class ContentFeed(
    val contentUrl: String,
    val desc: String,
    val publishedDate: String,
    val title: String
) : Parcelable