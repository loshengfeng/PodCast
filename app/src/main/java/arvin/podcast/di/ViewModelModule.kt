package arvin.podcast.di

import arvin.podcast.viewmodel.PodCastDetailViewModel
import arvin.podcast.viewmodel.PodCastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        PodCastViewModel(get())
    }

    viewModel {
        PodCastDetailViewModel(get())
    }
}