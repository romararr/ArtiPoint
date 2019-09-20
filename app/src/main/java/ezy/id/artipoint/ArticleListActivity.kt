package ezy.id.artipoint

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.article_list.*
import java.util.*
import kotlin.collections.ArrayList

class ArticleListActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var articles: MutableList<Articles>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list)

        // MENGAMBIL DATA DARI DATABASE FIREBASE
        ref = FirebaseDatabase.getInstance().getReference("Articles")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {

                    articles = ArrayList()

                    for (h in p0.children) {
                        val article = h.getValue(Articles::class.java)
//                        Log.d("data", article.toString())
                        articles.add(article!!)
                    }
                    recyclerArticle.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerArticle.adapter = ArticlesRecyclerAdapter(applicationContext,
                        articles as ArrayList<Articles>
                    )
                }
            }
        })

        // MENAMPILKAN JIKA DATA KOSONG
//        if (articles.isEmpty()) {
//            recyclerArticle.setVisibility(View.GONE);
//            empty_view.setVisibility(View.VISIBLE);
//        }
//        else {
//            recyclerArticle.setVisibility(View.VISIBLE);
//            empty_view.setVisibility(View.GONE);
//        }

        // KE HALAMAN MEMBUAT ARTIKEL
        addButton.setOnClickListener {
            startActivity(Intent(this, CreateArticleActivity::class.java))
        }
    }

}