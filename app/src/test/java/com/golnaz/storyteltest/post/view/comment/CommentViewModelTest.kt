package com.golnaz.storyteltest.post.view.comment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import com.golnaz.storyteltest.post.domain.usecases.GetCommentsUseCase
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CommentViewModelTest {
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var getCommentUseCase: GetCommentsUseCase
    private lateinit var comment: MutableList<Comment>

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
        comment = mutableListOf(Comment("name", 1, 1, "body", "email"))

        whenever(postRepository.getPostComments("2")).thenReturn(Single.just(comment))
        whenever(postRepository.getPostComments("1")).thenReturn(Single.just(comment))

        getCommentUseCase = GetCommentsUseCase(postRepository)
        commentViewModel = CommentViewModel(getCommentUseCase)
    }

    @Test
    fun `should show error when postId not equal to postId of comment`() {
        commentViewModel.getComments("2")
        assertEquals("2", commentViewModel.comments.value?.get(0)?.postId)
        assertFalse(commentViewModel.comments.value?.get(0)?.postId == 2)
    }

    @Test
    fun `shouldn't show error`() {
        commentViewModel.getComments("1")
        assertTrue(commentViewModel.comments.value?.get(0)?.postId == 1)
        assertTrue(commentViewModel.errors.value == null)
        assertTrue(commentViewModel.networkError.value == null)
    }

    @Test
    fun `should show error because of body text changed`() {
        commentViewModel.getComments("1")
        commentViewModel.comments.value?.get(0)?.body = "new body"
        assertTrue(commentViewModel.comments.value?.get(0)?.postId == 1)
        assertTrue(commentViewModel.errors.value == null)
        assertTrue(commentViewModel.networkError.value == null)
        assertTrue(commentViewModel.comments.value?.get(0)?.body == "body")
    }
}