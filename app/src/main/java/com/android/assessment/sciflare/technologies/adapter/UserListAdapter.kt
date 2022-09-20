package com.android.assessment.sciflare.technologies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.assessment.sciflare.technologies.databinding.LayoutListItemBinding
import com.android.assessment.sciflare.technologies.room.entity.User
import com.android.assessment.sciflare.technologies.support.OnClick

class UserListAdapter(c: Context, onClick: OnClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context? = null
    var listAdapter = ArrayList<User>()
    var onClick: OnClick? = null

    init {
        this.context = c
        this.onClick = onClick
    }

    fun addData(list: List<User>) {
        listAdapter.clear()
        listAdapter.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            val list = listAdapter[position]
            holder.view.textViewName.text = list.name
            holder.view.textViewMobile.text = list.mobile
            holder.view.textViewEmail.text = list.email
            holder.view.textViewGender.text = list.gender

            holder.view.imageViewEdit.setOnClickListener {
                onClick!!.userEdit(list)
            }

            holder.view.imageViewDelete.setOnClickListener {
                onClick!!.userDelete(list)
            }
        }
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    class MainViewHolder(val view: LayoutListItemBinding) : RecyclerView.ViewHolder(view.root) {}

}