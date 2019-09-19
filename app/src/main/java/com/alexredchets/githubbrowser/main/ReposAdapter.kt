package com.alexredchets.githubbrowser.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexredchets.githubbrowser.base.ItemClickListener
import com.alexredchets.githubbrowser.R
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter(val listener: ItemClickListener<Repo>): RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private var repos: List<Repo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false))

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repos[position]

        holder.name.text = item.name
        holder.desc.text = item.description
    }

    fun updateAdapter(repos: List<Repo>?) {
        if (!repos.isNullOrEmpty()) {
            this.repos = repos
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        this.repos = ArrayList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        var name: TextView = view.repoItemName
        var desc: TextView = view.repoItemDesc

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.onItemClicked(repos[adapterPosition])
        }
    }
}