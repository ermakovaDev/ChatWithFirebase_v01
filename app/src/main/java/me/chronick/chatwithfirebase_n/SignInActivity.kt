package me.chronick.chatwithfirebase_n

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import me.chronick.chatwithfirebase_n.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}