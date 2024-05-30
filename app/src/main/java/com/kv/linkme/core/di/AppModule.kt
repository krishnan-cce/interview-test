package com.kv.linkme.core.di

import androidx.room.Room
import com.kv.linkme.core.database.AppDatabase
import com.kv.linkme.presentation.screens.usersScreen.UserViewModel
import com.kv.linkme.domain.repository.UserRepository
import com.kv.linkme.data.repository.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "linkme"

        ).build()
    }

    single { get<AppDatabase>().userDao() }
    single<UserRepository> { UserRepositoryImpl(userDao = get()) }
    viewModel { UserViewModel(repository = get()) }
}