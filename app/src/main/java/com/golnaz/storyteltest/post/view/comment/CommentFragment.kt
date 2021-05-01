package com.golnaz.storyteltest.post.view.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.golnaz.storyteltest.R
import com.golnaz.storyteltest.databinding.CommentFragmentBinding
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.view.comment.adapter.CommentAdapter
import com.golnaz.storyteltest.utils.customDialogs.AlertDialogCallback
import com.golnaz.storyteltest.utils.customDialogs.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: CommentFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CommentViewModel by viewModels()

    private val args: CommentFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.comment_fragment, container, false)
        _binding?.viewModel = viewModel
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getComments(args.comment.posts)
        viewModel.postDetail.value = args.comment
        initAdapter()
        observeComments()
        observeError()
    }

    private fun initAdapter() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.commentList.layoutManager = layoutManager
        binding.commentList.adapter = adapter
    }

    private fun getComments(args: Post) {
        viewModel.getComments(args.id.toString())

    }

    private fun observeComments() {
        viewModel.comments.observe(requireActivity(), Observer { list ->
            adapter.fillData(list.toMutableList())
        })
    }

    private fun observeError() {
        viewModel.errors.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            viewModel.getComments(args.comment.posts.id.toString())
        })
        viewModel.networkError.observe(requireActivity(), Observer { error ->
            showDialog(error)
        })
    }

    private fun showDialog(error: String) {
        val callback = object : AlertDialogCallback {
            override fun onPositive() {
                getComments(args.comment.posts)
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