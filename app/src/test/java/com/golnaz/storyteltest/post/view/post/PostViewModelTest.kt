package com.golnaz.storyteltest.post.view.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.model.PostAndImages
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import com.golnaz.storyteltest.post.domain.usecases.GetPhotosUseCase
import com.golnaz.storyteltest.post.domain.usecases.GetPostsUseCase
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class PostViewModelTest {
    private lateinit var postViewModel: PostViewModel
    private lateinit var getPostsUseCase: GetPostsUseCase
    private lateinit var getPhotosUseCase: GetPhotosUseCase
    private lateinit var post: MutableList<Post>
    private lateinit var photo: MutableList<Photo>

    @Mock
    lateinit var postRepository: PostRepository


    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUpScheduler() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        post = mutableListOf(Post(1, "ds", "dsf", 1))
        photo = mutableListOf(Photo(1, 1, "dsp", "dsf", ""))

        whenever(postRepository.getPost()).thenReturn(Single.just(post))
        whenever(postRepository.getPhoto()).thenReturn(Single.just(photo))

        getPostsUseCase = GetPostsUseCase(postRepository)
        getPhotosUseCase = GetPhotosUseCase(postRepository)
        postViewModel = PostViewModel(getPostsUseCase, getPhotosUseCase)
    }

    @Test
    fun `shouldn't show error`() {
        postViewModel.getPosts()
        postViewModel.postsAndPhotos.value?.posts?.let { postViewModel.getPhotos(it) }

        assertEquals(postViewModel.postsAndPhotos.value, PostAndImages(post, photo))
        assertEquals(postViewModel.postsAndPhotos.value?.posts, post)
        assertEquals(postViewModel.postsAndPhotos.value?.photos, photo)
        assertTrue(postViewModel.showProgressBar.value == false)
    }

}