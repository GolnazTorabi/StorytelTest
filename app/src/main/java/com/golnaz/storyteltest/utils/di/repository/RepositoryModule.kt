package com.golnaz.storyteltest.utils.di.repository

import com.golnaz.storyteltest.post.data.repository.PostRepositoryImpl
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideEpisodeRepository(repo: PostRepositoryImpl): PostRepository = repo


}