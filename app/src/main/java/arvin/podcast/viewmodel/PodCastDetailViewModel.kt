package arvin.podcast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arvin.podcast.api.PodCastInterface
import arvin.podcast.api.data.PodCastDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PodCastDetailViewModel(private val podCastInterface: PodCastInterface) : ViewModel() {

    val podCastDetailLiveData = MutableLiveData<PodCastDetail>()

    fun getPodCastDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            val podDetail = podCastInterface.getPodCastDetail()
            podCastDetailLiveData.postValue(podDetail.data)
        }
    }
}