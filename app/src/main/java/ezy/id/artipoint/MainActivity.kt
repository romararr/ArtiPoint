package ezy.id.artipoint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    var register: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("Users")

        loginButton.setOnClickListener {
            if(register) registerData()
            else Toast.makeText(this, "Tunggu Bentar masih di bikin!!", Toast.LENGTH_SHORT).show()
        }
        registerButton.setOnClickListener { changeButtonAction() }
    }

    private fun changeButtonAction() {

        register = !register

        if (register) {
            loginButton.setText("D A F T A R")
            registerText.setText("Sudah punya akun? ")
            registerButton.setText("Yaudah masuk sih!")
        } else {
            loginButton.setText("M A S U K")
            registerText.setText("Belum punya akun? ")
            registerButton.setText("Daftar dulu dong...")
        }
    }

    private fun registerData() {
        val username = usernameTxt.text.toString()
        val password = passwordTxt.text.toString()

        val user = Users(username, password)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Oke syudah...", Toast.LENGTH_SHORT).show()
            usernameTxt.setText("")
            passwordTxt.setText("")
        }
    }
}
