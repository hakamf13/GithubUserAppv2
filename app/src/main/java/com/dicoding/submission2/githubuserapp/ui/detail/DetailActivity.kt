package com.dicoding.submission2.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submission2.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.submission2.githubuserapp.databinding.ActivityDetailBinding
import com.dicoding.submission2.githubuserapp.datasource.DetailUserResponse
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submission2.githubuserapp.token.ConstantToken.Companion.TAB_TITLES
import com.dicoding.submission2.githubuserapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailUsers = intent.extras?.get(EXTRA_DETAIL) as String
        val sectionsPagerAdapter = SectionsPagerAdapter(this, detailUsers)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabsFollow: TabLayout = binding.tabsFollows
        TabLayoutMediator(tabsFollow, viewPager) {tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailMainViewModel.detailUser.observe(this) { detailUserData ->
            getUserData(detailUserData)
        }

        detailMainViewModel.isLoading.observe(this) { loader ->
            showLoading(loader)
        }

        detailMainViewModel.getDetailUser(this, detailUsers)
    }

    private fun getUserData(userItems: DetailUserResponse){
        binding.apply {
            tvItemUsername.text = userItems.login
            tvItemName.text = userItems.name
            tvItemCompanyValue.text = userItems.company
            tvItemLocationValue.text = userItems.location
            tvRepoValue.text = userItems.publicRepos.toString()
            tvFollowerValue.text = userItems.followers.toString()
            tvFollowingValue.text = userItems.following.toString()
            Glide.with(this@DetailActivity)
                .load(userItems.avatarUrl)
                .circleCrop()
                .into(imgItemAvatar)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}