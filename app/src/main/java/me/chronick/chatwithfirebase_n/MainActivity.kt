package me.chronick.chatwithfirebase_n

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import me.chronick.chatwithfirebase_n.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("MyPath")

        binding.btnSend.setOnClickListener {
            myRef.setValue(binding.edtMessage.text.toString())
        }
        onChangeListener(myRef) // Path to listener
    }

    private fun onChangeListener (dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{ // callback
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    tvTextChat.append("\n")
                    tvTextChat.append("MyComp: ${snapshot.value.toString()}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}