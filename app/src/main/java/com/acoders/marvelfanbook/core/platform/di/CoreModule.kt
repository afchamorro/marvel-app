package com.acoders.marvelfanbook.core.platform.di

import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object CoreModule {

    @Provides
    fun provideRecycleDelegateAdapter() = RecycleViewDelegateAdapter()

}