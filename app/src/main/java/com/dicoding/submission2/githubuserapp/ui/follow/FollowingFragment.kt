package com.dicoding.submission2.githubuserapp.ui.follow

//import com.dicoding.submission2.githubuserapp.adapter.ListUserAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.githubuserapp.adapter.FollowsAdapter
import com.dicoding.submission2.githubuserapp.databinding.FragmentFollowingBinding
import com.dicoding.submission2.githubuserapp.datasource.ItemsItem
import com.dicoding.submission2.githubuserapp.token.ConstantToken
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.USERNAME
import com.dicoding.submission2.githubuserapp.ui.detail.DetailActivity
import com.dicoding.submission2.githubuserapp.viewmodel.MainViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private val followingViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        followingViewModel.following.observe(viewLifecycleOwner) { followingData ->
            if (followingData == null) {
                val dataUsers = arguments?.getString(USERNAME) ?: ""
                followingViewModel.getFollowingData(requireActivity(), dataUsers)
            } else {
                showFollowing(followingData)
            }
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowing(dataUsers: List<ItemsItem>) {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        val userAdapter = FollowsAdapter(dataUsers)
        binding.rvFollowing.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : FollowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(ConstantToken.EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}