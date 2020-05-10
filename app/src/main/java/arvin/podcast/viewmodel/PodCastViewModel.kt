package arvin.podcast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arvin.podcast.api.PodCastInterface
import arvin.podcast.api.data.PodCastList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PodCastViewModel(private val podCastInterface: PodCastInterface) : ViewModel() {

    val podCastsLiveData = MutableLiveData<PodCastList>()

    fun getPodCastList() {
        viewModelScope.launch(Dispatchers.IO) {
            val podCasts = podCastInterface.getPodCastList()
            podCastsLiveData.postValue(podCasts.data)
        }
    }
}