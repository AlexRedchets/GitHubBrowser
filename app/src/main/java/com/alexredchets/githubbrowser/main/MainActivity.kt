package com.alexredchets.githubbrowser.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_bottom.*

import com.alexredchets.githubbrowser.*
import com.alexredchets.githubbrowser.base.ItemClickListener
import com.alexredchets.githubbrowser.base.formatDate
import com.alexredchets.githubbrowser.base.hideKeyboard

class MainActivity : AppCompatActivity(),
    ItemClickListener<Repo> {

    private lateinit var reposAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init Recycler View and Adapter
        reposRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reposAdapter = ReposAdapter(this)
        reposRecyclerView.adapter = reposAdapter

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getUser().observe(this, Observer {
            user ->
            when {
                // case when user name is null https://api.github.com/users/repo
                user == null -> {
                    errorMessage.text = resources.getString(R.string.profile_error)
                    errorMessage.visibility = VISIBLE
                    reposAdapter.clearList()
                }
                (user.avatar_url.isEmpty() && user.name.isNullOrEmpty()) -> {
                    errorMessage.text = resources.getString(R.string.network_error)
                    errorMessage.visibility = VISIBLE
                    reposAdapter.clearList()
                }
                else -> {
                    errorMessage.visibility = GONE
                    reposAdapter.clearList()
                }
            }
            userName.text = user?.name
            Glide
                .with(this)
                .load(user?.avatar_url)
                .into(userImage)
        })
        viewModel.getRepos().observe(this, Observer {
            repos -> reposAdapter.updateAdapter(repos)
        })

        buttonSearch.setOnClickListener {
            hideKeyboard(this)
            viewModel.loadUser(userEditText.text.toString())
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onItemClicked(item: Repo) {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.dialog_bottom)

        dialog.dialogLastUpdatedAns.text =
            formatDate(item.updated_at)
        dialog.dialogStarsAns.text = item.stargazers_count
        dialog.dialogForksAns.text = item.forks

        dialog.show()
    }
}
