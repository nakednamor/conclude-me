package com.nakednamor.conclude.me.di

import android.content.Context
import com.nakednamor.conclude.me.data.AppDatabase
import com.nakednamor.conclude.me.data.weight.WeightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideWeightDao(appDatabase: AppDatabase): WeightDao = appDatabase.weightDao()
}