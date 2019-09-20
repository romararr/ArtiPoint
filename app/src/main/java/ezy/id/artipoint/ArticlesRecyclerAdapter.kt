package ezy.id.artipoint

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.article_list_item.*
import kotlinx.android.synthetic.main.article_list_item.gambarArticle
import kotlinx.android.synthetic.main.create_article.*
import kotlin.math.log

class ArticlesRecyclerAdapter(private val context: Context, private val items: ArrayList<Articles>) :
    RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(items.get(position))
        holder.itemList.setOnClickListener {
            val intent = Intent(context, DetailArticle::class.java)
            intent.putExtra("ARTICLE", items[position])
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Articles) {
            judulArticleTxt.text = item.judul
            descArticleTxt.text = item.deskripsi
            Glide.with(containerView)
                .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.camera))
                .load(item.pathGambar)
                .into(gambarArticle)
        }
    }
}