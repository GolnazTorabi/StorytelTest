package com.golnaz.storyteltest.post.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.golnaz.storyteltest.R
import com.golnaz.storyteltest.databinding.PostFragmentBinding
import com.golnaz.storyteltest.post.view.post.adapter.PostAdapter
import com.golnaz.storyteltest.utils.customDialogs.AlertDialogCallback
import com.golnaz.storyteltest.utils.customDialogs.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : Fragment() {
    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()

    private var subscribeComment: Disposable? = null

    @Inject
    lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false)
        _binding?.viewModel = viewModel
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initAdapter()
    }

    private fun initAdapter() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        subscribeComment = adapter.clickEventComment
            .subscribe { post ->
                val action = PostFragmentDirections.actionPostFragmentToCommentFragment(post)
                findNavController().navigate(action)
            }
    }


    private fun getData() {
        viewModel.getPosts()
        observePostsData()
        observePostsError()
    }

    private fun observePostsData() {
        viewModel.postsAndPhotos.observe(requireActivity(), Observer { data ->
            adapter.fillData(data)
        })
    }

    private fun observePostsError() {
        viewModel.errors.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            //viewModel.getPosts()
        })
        viewModel.networkError.observe(requireActivity(), Observer { error ->
            if (error.isNullOrEmpty())
                showDialog(error)
        })
    }

    private fun showDialog(error: String) {
        val callback = object : AlertDialogCallback {
            override fun onPositive() {
                getData()
            }

            override fun onNegative() {
            }

        }
        val dialog = CustomAlertDialog(
            requireActivity(), callback,
            error,
            "retry", "close"
        )
        dialog.show()
    }

}