package co.com.personal.hnino.pruebaceiba.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.com.personal.hnino.pruebaceiba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //Implementacion de view bindig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //Implementacion de view bindig

        init()
    }

    fun init(){

        binding.progressCircular.isVisible = true

        Handler(Looper.getMainLooper()).postDelayed({
            //El codigo existente en este bloque de codigo, se ejecutará después de 5000 msg.
            binding.progressCircular.isVisible = false
            verListUsersActivity()
        }, 5000)
    }

    fun verListUsersActivity(){

        val intent = Intent(this, ListUsersActivity::class.java)
        startActivity(intent)
        finish()
    }
}