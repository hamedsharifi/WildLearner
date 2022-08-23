package com.wildlearner.di

import com.wildlearner.baseviewmodel.coroutineDispatcherProvider
import com.wildlearner.core.repository.SearchRepository
import com.wildlearner.core.usecase.SearchUseCase
import com.wildlearner.network.Services
import com.wildlearner.presentation.SearchViewModel
import com.wildlearner.repository.SearchRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel {
        SearchViewModel(
            coroutineDispatcherProvider = coroutineDispatcherProvider(),
            get()
        )
    }

    single<Services> {
        Services.getInstance()
    }

    single<SearchRepository> { SearchRepositoryImpl(get()) }

    single {
        SearchUseCase(get())
    }
}