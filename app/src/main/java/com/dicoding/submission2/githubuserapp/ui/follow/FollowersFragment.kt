package com.dicoding.submission2.githubuserapp.ui.follow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.githubuserapp.adapter.FollowsAdapter
//import com.dicoding.submission2.githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission2.githubuserapp.databinding.FragmentFollowersBinding
import com.dicoding.submission2.githubuserapp.datasource.ItemsItem
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.USERNAME
import com.dicoding.submission2.githubuserapp.ui.detail.DetailActivity
import com.dicoding.submission2.githubuserapp.viewmodel.MainViewModel

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)

        followersViewModel.followers.observe(viewLifecycleOwner) { followersData ->
            if (followersData == null) {
                val dataUsers = arguments?.getString(USERNAME)?:""
                followersViewModel.getFollowersData(requireActivity(), dataUsers)
            } else {
                showFollowers(followersData)
            }
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowers(dataUsers: List<ItemsItem>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        val userAdapter = FollowsAdapter(dataUsers)
        binding.rvFollowers.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : FollowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}