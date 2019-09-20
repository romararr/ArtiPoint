package ezy.id.artipoint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.detail_article.*

class DetailArticle: AppCompatActivity() {

    val article = getIntent().getExtras()?.getSerializable("ARTICLE") as? Articles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_article)

        Glide.with(this)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.camera))
            .load(article?.pathGambar)
            .into(detailGambar)
        detailJudul.text = article?.judul
        detailDeskripsi.text = article?.deskripsi

    }
}