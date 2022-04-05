package id.wendei.store.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.wendei.store.data.local.room.AppDatabase
import id.wendei.store.data.local.room.StoreDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, appContext.packageName).build()

    @Singleton
    @Provides
    fun provideStoreDao(appDatabase: AppDatabase): StoreDao = appDatabase.storeDao()

}