package ezy.id.artipoint

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.article_list_item.*
import kotlin.math.log

class ArticlesRecyclerAdapter(private val context: Context, private val items: ArrayList<Articles>) :
    RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position))
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Articles) {
            judulArticleTxt.text = item.judul
            descArticleTxt.text = item.deskripsi
        }

    }
}