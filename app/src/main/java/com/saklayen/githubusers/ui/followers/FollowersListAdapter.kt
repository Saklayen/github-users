package com.saklayen.githubusers.ui.followers

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saklayen.githubusers.base.utils.layoutInflater
import com.saklayen.githubusers.clearDecorations
import com.saklayen.githubusers.databinding.ItemRowUserBinding
import com.saklayen.githubusers.domain.model.Follower
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FollowersListAdapter(val viewModel: FollowersViewModel) :
    ListAdapter<Follower, FollowersListViewHolder>(
        UserDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersListViewHolder {
        return FollowersListViewHolder(
            ItemRowUserBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FollowersListViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }
}


@ExperimentalCoroutinesApi
class FollowersListViewHolder(val binding: ItemRowUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: FollowersViewModel, data: Follower) {
        binding.viewModel = viewModel
        binding.item = data
        binding.executePendingBindings()
    }
}

private class UserDiffCallback : DiffUtil.ItemCallback<Follower>() {
    override fun areItemsTheSame(oldItem: Follower, newItem: Follower) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Follower, newItem: Follower) =
        oldItem == newItem
}

@ExperimentalCoroutinesApi
@BindingAdapter(value = ["bindFollowersViewModel", "bindDataList"], requireAll = true)
fun RecyclerView.bindDashboardItemListAdapter(
    viewModel: FollowersViewModel,
    data: List<Follower>?
) {
    if (adapter == null) adapter = FollowersListAdapter(viewModel)
    val value = data ?: emptyList()
    val feedListAdapter = adapter as? FollowersListAdapter
    feedListAdapter?.submitList(value)
    clearDecorations()
}
