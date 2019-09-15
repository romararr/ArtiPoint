package ezy.id.artipoint

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.create_article.*
import java.io.IOException

class CreateArticleActivity: AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    // FIREBASE STORAGE
    lateinit var firebaseStore: FirebaseStorage
    lateinit var storageReference: StorageReference

    // FIREBASE DATABASE
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_article)

        // SET STORAGE REFERENCE
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("images")

        // SET DATABASE REFERENCE
        ref = FirebaseDatabase.getInstance().getReference("Articles")

        headerPickImage.setOnClickListener { openGallery() }
        backButton.setOnClickListener { finish() }
        createButton.setOnClickListener { uploadData() }
    }

    // MEMBUKA GALERI
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // ACTION SETELAH MEMILIH GAMBAR DARI GALERI
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            if(data == null || data.data == null) return

            // MASUKNYA KE VARIABLE filePath
            filePath = data.data

            try {
                Glide.with(this)
                    .load(filePath)
                    .into(gambarArticle)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // UPLOAD KE DATABASE
    private fun uploadData(){

        val judul = judul.text.toString()
        val deskripsi = deskripsi.text.toString()

       if(filePath != null || judul!!.isNotEmpty() || deskripsi!!.isNotEmpty()){
           val uploadTask = storageReference!!.putFile(filePath!!)

           // INI UPLOAD GAMBAR DULU
           val task = uploadTask.continueWithTask { task ->
               if (!task.isSuccessful) Toast.makeText(this, "Gagal Upload Data", Toast.LENGTH_SHORT).show()
               storageReference.downloadUrl
           }.addOnCompleteListener{task ->
               if(task.isSuccessful) {
                   val downloadUrl = task.result
                   val uri = downloadUrl!!.toString().substring(0, downloadUrl.toString().indexOf("&token"))

                   val article = Articles(judul.toString(), deskripsi.toString(), uri)
                   val articleId = ref.push().key.toString()

                   // KALO SUCCESS BARU UPLOAD DATANYA YANG LAIN
                   ref.child(articleId).setValue(article).addOnCompleteListener {
                       finish()
                       Toast.makeText(this, "Oke syudah di upload...", Toast.LENGTH_SHORT).show()
                   }
               }
           }

       }else{
           Toast.makeText(this, "Lengkapi form yang ada...", Toast.LENGTH_SHORT).show()
       }
    }
}