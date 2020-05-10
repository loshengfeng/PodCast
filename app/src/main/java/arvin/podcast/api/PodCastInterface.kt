package arvin.podcast.api

import arvin.podcast.api.data.Data
import arvin.podcast.api.data.PodCastDetail
import arvin.podcast.api.data.PodCastList
import retrofit2.http.GET

interface PodCastInterface {
    @GET("getcasts")
    suspend fun getPodCastList(): Data<PodCastList>

    @GET("getcastdetail")
    suspend fun getPodCastDetail(): Data<PodCastDetail>
}