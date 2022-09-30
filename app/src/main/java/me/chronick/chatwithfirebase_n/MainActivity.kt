package me.chronick.chatwithfirebase_n

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import me.chronick.chatwithfirebase_n.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setUpActionBar()

        val database = Firebase.database
        val myRef = database.getReference("MyPath")

        binding.btnSend.setOnClickListener {
            myRef.setValue(binding.edtMessage.text.toString())
        }

        binding.btnSignOut.setOnClickListener {
            auth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()


        }
        onChangeListener(myRef) // Path to listener
    }

    private fun onChangeListener (dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{ // callback
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    tvTextChat.append("\n")
                    tvTextChat.append("${auth.currentUser?.displayName} : ${snapshot.value.toString()}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setUpActionBar(){
        val actionBar = supportActionBar
        Thread { // secondary thread
            val bitMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val drawableIcon = BitmapDrawable(resources, bitMap)  // drawable to bitmap
            runOnUiThread{ // main thread
                actionBar?.setDisplayHomeAsUpEnabled(true)
                actionBar?.setHomeAsUpIndicator(drawableIcon)
            }
        }.start()
    }
}