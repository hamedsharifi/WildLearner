package com.wildlearner.presentation


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wildlearner.R
import com.wildlearner.core.model.Results


class SearchResultsAdapter(
    public val mList: List<Results>
) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.title.text = item.title
        holder.link.text = item.link
        holder.description.text = item.description
        holder.link.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(item.link)
            holder.link.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val description: TextView = itemView.findViewById(R.id.description)
    }
}