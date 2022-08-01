package co.com.personal.hnino.pruebaceiba.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.domain.model.PostUsersModel
import co.com.personal.hnino.pruebaceiba.ui.view.adapters.AdapterRecyclerViewPostsUsers
import co.com.personal.hnino.pruebaceiba.ui.viewmodel.PostsUsersViewModel
import co.com.personal.hnino.pruebaceiba.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoUserActivity  : AppCompatActivity() {

    private val itemsUsersViewModel : UsersViewModel by viewModels()
    private val itemsPostsUsersViewModel : PostsUsersViewModel by viewModels()

    private lateinit  var listPostsUsersForRecyclerView:List<PostUsersModel>
    private lateinit var adapter: AdapterRecyclerViewPostsUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_user)

        init()
    }

    fun init(){

        val tvNombreUser2 = findViewById<TextView>(R.id.tvNombreUser2)
        val tvNumTelefonico2 = findViewById<TextView>(R.id.tvNumTelefonico2)
        val tvEmail2 = findViewById<TextView>(R.id.tvEmail2)
        val btnVolver = findViewById<Button>(R.id.btnVolver)



        itemsUsersViewModel.getItemUserModelSelect()

        itemsUsersViewModel.UserSelectMLD.observe(this, Observer {
            Toast.makeText(this," ---- " + it.nombre + " ---- ", Toast.LENGTH_SHORT).show()

            tvNombreUser2.text = it.nombre
            tvNumTelefonico2.text = it.phone
            tvEmail2.text = it.email
            var queryPostsByIdUser: String = "posts?userId=" + it.id.toString()

            searchByKeyWords(queryPostsByIdUser)
        })

        btnVolver.setOnClickListener { volver() }
    }

    private fun searchByKeyWords(queryPostsByIdUser: String) {
        println("========== La Palabla clave para buscar PostsUsers es: $queryPostsByIdUser") // Prueba de Escritorio

        var progressCircularPost: ProgressBar = findViewById(R.id.progressCircularPosts)

        itemsPostsUsersViewModel.getListPostsUsers(queryPostsByIdUser)

        itemsPostsUsersViewModel.listPostsUsersMLD.observe(this, Observer{
            listPostsUsersForRecyclerView = it

            println(" =========> la lista a mostrar en el Recycler View de objetos PostUsersModel es =>" +
                    " ${listPostsUsersForRecyclerView.toString()}")

            if (it.isEmpty()) {

                Toast.makeText(
                    this,
                    "No se encontraron publicaciones para este usuario",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                iniciarReciclerViewPostsUsers()
                adapter.notifyDataSetChanged() // con esta función le avisamos al adapter que ha habido cambios

                Toast.makeText(
                    this,
                    "Respuesta a llamada OK",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        itemsPostsUsersViewModel.isLoading.observe(this, Observer {

            progressCircularPost.isVisible = it // Muestra u oculta la barra circular de proceso
        })
    }

    private fun iniciarReciclerViewPostsUsers() {
        val manager = LinearLayoutManager(this) // si cambiaramos LinearLayoutManager(this)
        // por GridLayoutManager(this, 2) nos permite visualizar 2 items en en una sola linea, y si en ves del 2
        // fuera un 3, veriamos 3 items en una sola linea

        var recyclerViewPostsUsers = findViewById<RecyclerView>(R.id.RViewPostsUsers)
        recyclerViewPostsUsers.layoutManager = manager
        adapter = AdapterRecyclerViewPostsUsers(listPostsUsersForRecyclerView)
        recyclerViewPostsUsers.adapter = adapter // Al recyclerView le estamos asignando el adaptador que creamos
        // AdapterRecyclerViewPostsUsers y al que le enviamos como parametro la lista de objetos que vamos a mostrar
        val decoration = DividerItemDecoration(this,manager.orientation) // Creamos la variable decoration que nos
        // nos ayudara a separar los items del reciclerView con una linea horizontal y le asigamos la función
        // DividerItemDecoration a la que como parametros se le envian el contexto y la orientacion que
        // sería el mismo LinearLayoutManager(this) el cual es la misma variable que creamos llamada manager.
        recyclerViewPostsUsers.addItemDecoration(decoration) //le adicionamos al reciclerView la decoración que creamos
        // con la variable "decoration"
    }

    private fun volver() {
        val intent = Intent(this, ListUsersActivity::class.java)
        startActivity(intent)
        //onBackPressed() //Retorna a la Actividad y vista anterior
        finish() // Finaliza la activity InfoUserActivity
    }
}