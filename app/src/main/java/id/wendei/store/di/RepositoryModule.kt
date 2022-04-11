package id.wendei.store.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.wendei.store.data.local.room.StoreDao
import id.wendei.store.data.remote.api.StoreService
import id.wendei.store.domain.repository.StoreRepository
import id.wendei.store.data.repository.StoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStoreRepository(storeService: StoreService, storeDao: StoreDao): StoreRepository =
        StoreRepositoryImpl(storeService = storeService, storeDao = storeDao)

}