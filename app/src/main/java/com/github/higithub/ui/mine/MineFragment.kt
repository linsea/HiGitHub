package com.github.higithub.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.higithub.databinding.FragmentMineBinding
import com.github.higithub.db.UserProfileRepository
import com.github.higithub.db.getDatabase
import com.github.higithub.login.AuthManager
import com.github.higithub.model.GithubUserModel
import com.github.higithub.network.ApiService
import com.github.higithub.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar


class MineFragment : BaseFragment() {

    private lateinit var mineViewModel: MineViewModel
    private var _binding: FragmentMineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val database = getDatabase(requireContext())
        val repository = UserProfileRepository(ApiService, database.userProfileDao)

        mineViewModel = ViewModelProvider(this, MineViewModel.FACTORY(repository))
            .get(MineViewModel::class.java)

        _binding = FragmentMineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.login.setOnClickListener {
            AuthManager.startGithubWebAuth(requireActivity())
        }

        binding.logout.setOnClickListener {
            mineViewModel.logout()
        }

        mineViewModel.logEvent.observe(viewLifecycleOwner, {
            if (it.isLogin) {
                binding.login.visibility = View.GONE
                showLoadingView()
            } else {
                hideLoadingView()
                binding.login.visibility = View.VISIBLE
                binding.content.visibility = View.GONE
            }
        })

        mineViewModel.userInfo.observe(viewLifecycleOwner, {
            if (it != null) {
                showUserInfo(it)
            } else {
                Toast.makeText(requireContext(), "Load User Info Error", Toast.LENGTH_LONG).show()
            }
        })

        mineViewModel.message.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(requireContext(), "Login Error: $it", Toast.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            mineViewModel.registerEventBus()
        }

        return root
    }

    private fun showUserInfo(userModel: GithubUserModel) { // TODO : more info to set to view
        binding.content.visibility = View.VISIBLE
        binding.name.text = userModel.name
        Glide.with(this)
            .load(userModel.avatar_url)
            .into(binding.avatar)
        binding.allInfo.text = userModel.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}