package co.com.personal.hnino.pruebaceiba.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout

import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.databinding.ActivityListUsersBinding
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import co.com.personal.hnino.pruebaceiba.ui.view.adapters.AdapterRecyclerViewUsers
import co.com.personal.hnino.pruebaceiba.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // Con esta etiqueta configuramos esta activity y la preparamos para que permita inyectar  dependencias
// haciendo uso de Dagger Hilt, Una dependencia es una clase que es necesario usar en otras clases, y para ello es necesario
// crear instancias de esas clases (dependencias), sin embargo al usar Dagger Hilt, lo que hacemos es inyectar las clases
// que necesitamos, y no nececitamos crear instancias de las mismas ya que Dagger Hilt hace esa labor cuando es requerido .
// La idea de implementar Inyección de dependencias es que dentro de una clase, no se tenga ninguna instacia de ninguna otra clase,
// ya que todas deberian ser inyectadas y gestionadas por Dagger Hilt

class ListUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListUsersBinding //implementamos view Binding


    private lateinit  var listUsersForRecyclerView:List<UsersModel>
    private lateinit var adapter: AdapterRecyclerViewUsers

    private val itemsUsersViewModel : UsersViewModel by viewModels() // hacemos la conexion y logica de nuestra viewModel
    // a la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_users)
        binding = ActivityListUsersBinding.inflate(layoutInflater) //implementamos view Binding
        setContentView(binding.root) //implementamos view Binding

        init()
    }

    fun init(){

        searchByKeyWords("users")
    }


    //********************************** INICIO MENU PRINCIPAL **********************************//

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu) // Cargamos el recurso existente en la carpeta res/menu/main_menu.xml
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.cerrarSesion -> {

                itemsUsersViewModel.deleteAllFromDataBase()// //Este metodo va a borrar toda la la información
                // de la base de datos

                finish() // Finaliza la activity RecyclerViewApoloFavoritosActivity
                Toast.makeText(this,
                    "Por seguridad, todos los datos que estaban almacenados han sido borrados.",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
    //********************************** FIN MENU PRINCIPAL **********************************//

    private fun searchByKeyWords(queryByKeyWords: String) {
        println("========== La Palabla clave para buscar usuarios es: $queryByKeyWords") // Prueba de Escritorio

        // Al tietToQuery1 le implementamos los metodos de escucha para detectar detecar cuando se ingresa o se borra
        // una letra y asi poder buscar y mostrar al usuario la lista de objetos de tipo UserModel filtrada por las
        // palabras clave que el usuario va ingresando en el campo tietToQuery1

        binding.tietToQuery1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (binding.tietToQuery1.text.toString().isNullOrEmpty()){
                    searchByKeyWords("users")
                }
                else{

                    val listUsersForRecyclerViewSearch: MutableList<UsersModel> = mutableListOf()

                    listUsersForRecyclerView.map {
                        if(it.nombre.lowercase().indexOf(binding.tietToQuery1.text.toString().lowercase()) != -1){
                            //si el contenido escrito por el usuario existe entonces =>
                            listUsersForRecyclerViewSearch.add(it)
                        }
                    }

                    listUsersForRecyclerView = listUsersForRecyclerViewSearch

                    iniciarReciclerViewUsers()
                }
                binding.tvListEmpty.isVisible = listUsersForRecyclerView.isNullOrEmpty()
                // Si listUsersForRecyclerView.isNullOrEmpty() es verdadero entonces tvListEmpty.isVisible = true
                // y si es falso entonces tvListEmpty.isVisible = false
            }
        })


        itemsUsersViewModel.getListUsers(queryByKeyWords)

        itemsUsersViewModel.listUsersMLD.observe( this, Observer{

            listUsersForRecyclerView = it

            println(" ===============>>>> la lista a mostrar en el Recycler View de objetos UsersModel es =>" +
                    " ${listUsersForRecyclerView.toString()}")

            if (it.isEmpty()) {

                Toast.makeText(
                    this,
                    "No se encontraron resultados para esta busqueda",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                iniciarReciclerViewUsers()
                adapter.notifyDataSetChanged() // con esta función le avisamos al adapter que ha habido cambios

                println("============> Respuesta a llamada OK")
            }

        })

        itemsUsersViewModel.isLoading.observe(this, Observer {
            binding.viewContainerListUsers.isVisible = !it
            binding.viewContainerListUsersLoading.isVisible = it //true Muestra pantalla Shimmer Loading
            //binding.progressCircularUsers.isVisible = it // Muestra u oculta la barra circular de proceso
        })
    }

    private fun iniciarReciclerViewUsers() {
        val manager = LinearLayoutManager(this) // si cambiaramos LinearLayoutManager(this)
        // por GridLayoutManager(this, 2) nos permite visualizar 2 items en en una sola linea, y si en ves del 2
        // fuera un 3, veriamos 3 items en una sola linea

        var recyclerViewUsers = findViewById<RecyclerView>(R.id.RViewUsers)
        recyclerViewUsers.layoutManager = manager
        adapter = AdapterRecyclerViewUsers(listUsersForRecyclerView,{UserModel -> itemSeleccionado(UserModel)})
        recyclerViewUsers.adapter = adapter // Al recyclerView le estamos asignando el adaptador que creamos
        // AdapterRecyclerViewUsers y al que le enviamos como parametro la lista de objetos que vamos a mostrar
        // y tambien le enviamos la función lamda que nos ayudara a traer de vuelta a esta actividad el
        // objeto(con toda su data) que el usuario a seleccionado. Tener en cuenta que las funciones lamda deben
        // ir entre llaves y  no necesariamente deben ir dentro de parentesis de los argumentos,
        // por lo que la podemos sacar de los mismos
        /*
        val decoration = DividerItemDecoration(this,manager.orientation) // Creamos la variable decoration que nos
        // nos ayudara a separar los items del reciclerView con una linea horizontal y le asigamos la función
        // DividerItemDecoration a la que como parametros se le envian el contexto y la orientacion que
        // sería el mismo LinearLayoutManager(this) el cual es la misma variable que creamos llamada manager.

        recyclerViewUsers.addItemDecoration(decoration) //le adicionamos al reciclerView la decoración que creamos
        // con la variable "decoration"
         */
    }

    fun itemSeleccionado(usersModel: UsersModel){
        //Esta función recibe como parametro el objeto de tipo UserModel que trae la data del objeto (Item) al que
        // le hacimos click del recycler view, lo que estamos haciendo es recuperar el objeto seleccionado por usuario
        // en el recyclerview y lo traemos a esta actividad, para ello usamos nos es util la funcion lamda
        itemsUsersViewModel.setItemUserModelSelect(usersModel)

        itemsUsersViewModel.getItemUserModelSelect()

        itemsUsersViewModel.UserSelectMLD.observe(this, Observer {
            Toast.makeText(this," ---- " + it.nombre + " ---- ", Toast.LENGTH_SHORT).show()
        })

        iniciarDetallesUserModel()
    }

    private fun iniciarDetallesUserModel() {

        val intent = Intent(this, InfoUserActivity::class.java)
        startActivity(intent)
        //Toast.makeText(this, "Detalles del item seleccionado =)", Toast.LENGTH_SHORT).show()
        finish()
    }


    private fun ocultarKeyboard() {
        //Esta función oculta el teclado despues de que se usa el searchViewImgs
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val contenedorRecyclerViewUsers = findViewById<ConstraintLayout>(R.id.contenedorRecyclerViewUsers)
        imm.hideSoftInputFromWindow(contenedorRecyclerViewUsers.windowToken, 0)
    }
}