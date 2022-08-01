package co.com.personal.hnino.pruebaceiba.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.com.personal.hnino.pruebaceiba.data.model.DataTemporalProvider
import co.com.personal.hnino.pruebaceiba.domain.DeleteAllFromDataBaseUseCase
import co.com.personal.hnino.pruebaceiba.domain.GetListUsersUseCase
import co.com.personal.hnino.pruebaceiba.domain.model.AddressModel
import co.com.personal.hnino.pruebaceiba.domain.model.CompanyModel
import co.com.personal.hnino.pruebaceiba.domain.model.GeoModel
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi // Esta anotación nos quita los warning en los dispachers ya que es experimental
class UsersViewModelTest{// aqui podemos lanzar todos los test al mismo tiempo

    @RelaxedMockK // Si no preparamos y definimos una respuesta mockeada de los metodos de la clase que  vamos a Testiar,
    //es decir de la clase usersViewModel que declaramos mas adelante, entonces esta etiqueta nos ayuda a generar
    // esa respuesta automaticamente y el test funciona normalmente.

    //@MockK // Si no preparamos y definimos una respuesta mockeadaa de los metodos de la clase que  vamos a Testiar,
    // es decir de la clase usersViewModel que declaramos mas adelante, entonces esta etiqueta y/o anotacion intentara
    // acceder a un metodo que no hemos preparado y por tanto el test falla, Esta anotación No nos ayuda a generar esa
    // respuesta automaticamente como si lo hace @RelaxedMockK. Lo mejor es usar la anotación @MockK, debido a
    // que tenemos control total de los test, pero en este ejemplo vamos a usar @RelaxedMockK para fascilitar el proceso.

    private lateinit var getListUsersUseCase: GetListUsersUseCase //Declaramos la variable getListUsersUseCase que tiene
    // configurda la anotación @RelaxedMockK que sera necesaria como parametro para poder crear mas adelante una
    // instancia de usersViewModel
    @RelaxedMockK
    private lateinit var dataTemporalProvider: DataTemporalProvider//Declaramos la variable dataTemporalProvider que tiene
    // configurda la anotación @RelaxedMockK que sera necesaria como parametro para poder crear mas adelante una
    // instancia de usersViewModel
    @RelaxedMockK
    private lateinit var deleteAllFromDataBaseUseCase: DeleteAllFromDataBaseUseCase//Declaramos la variable deleteAllFromDataBaseUseCase
    // que tiene configurda la anotación @RelaxedMockK que sera necesaria como parametro para poder crear mas adelante una
    // instancia de usersViewModel

    private lateinit var usersViewModel: UsersViewModel // creamos una instancia de la clase que queremos testear,
    // y como para este caso es una clase de tipo view Model, entonces para poder hacer este test, debemos usar
    // la libreria testImplementation "androidx.arch.core:core-testing:2.1.0" que configuramos en el
    // build.gradle (app) y que nos ayuda a testear los liveData que son usados en las clases view model
    // y ademas de ello, tambien debemos utilizar una regla.

    // Una Regla es basicamente una función en el OnBefore, pero es abstraida, ya que es más reutilizable y
    // disminuye el boilerplate que son esas secciones de código que deben incluirse en muchos lugares
    // con poca o ninguna alteración.

    @get:Rule // esta etiqueta define que el codigo siguiente es una regla
    var regla: InstantTaskExecutorRule = InstantTaskExecutorRule() // creamos una variable llamada regla y la instanciamos con
    // la clase InstantTaskExecutorRule la cual hace parte de la libreria
    // testImplementation "androidx.arch.core:core-testing:2.1.0", por eso al implementarla se debe
    // usar el import androidx.arch.core.executor.testing.InstantTaskExecutorRule

    // OJO ==> en la clase UsersViewModel se esta usando la corrutina viewModelScope.launch {}, y cuando se hace testing
    // no podemos emular bien el ciclo de vida de esta corrutina, por tal motivo debemos modificar el dispacher en la funcion
    // onBefore (Los dispacher son los que se encargan de gestionar los hilos que usan las corrutinas).

    @Before
    fun onBefore(){
        //el codigo existente en esta función se va a ejecutar antes de los test debido a que esta
        // funcion esta configurada con la etiqueta @Before de la libreria junit.
        MockKAnnotations.init(this) // inicializamos la libreria de Mock para mockear el repositorio en cuestion

        usersViewModel = UsersViewModel(getListUsersUseCase, dataTemporalProvider, deleteAllFromDataBaseUseCase)
        // cremos una instancia de la clase UsersViewModel que es la que queremos testear

        Dispatchers.setMain(Dispatchers.Unconfined) // iniciamos el dispacher antes de los test ya que en esta
        //función estamos usando la anotación @Before

    }

    @After
    fun onAfter(){
        //el codigo existente en esta función se va a ejecutar despues de los test debido a que esta
        // funcion esta configurada con la etiqueta @After de la libreria junit.
        Dispatchers.resetMain() // cerramos el hilo creado para continuar con el hilo principal
    }

    @Test
    fun `when the function getListUsers is used, it gets all of the users`() = runTest{
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runTest nos ayuda a lanzar corrutinas, aqui no usamos runBlocking, por que aqui
        // trabajamos com view Model

        //Given =>
        val listUserModelTest = listOf(
            UsersModel(1, "Henry Francisco Niño", "hnino","henrybmx@gmail.com",
                AddressModel("cll 72","suite 1","Bogota D.C.", "110021", GeoModel("1111111","22222222")),
                "3103354642","www.website1.com", CompanyModel("Company1 SAS","catchPhrase 1", "bs 1")
            ),

            UsersModel(2, "Cesar Alejandro Niño", "ceang","cesaralejo@gmail.com",
                AddressModel("cll 73","suite 2","Garagoa D.C.", "150021", GeoModel("3333333","44444444")),
                "3118571599","www.website2.com", CompanyModel("Company2 SAS","catchPhrase 2", "bs 2")
            )
        )

        coEvery { getListUsersUseCase("users") } returns  listUserModelTest

        //When
        usersViewModel.getListUsers("users") // cuendo se use el metodo getListUsers("users") de la clase usersViewModel

        //Then
        coVerify(exactly = 1) { getListUsersUseCase("users")} // El test verifica que se esta llamando una sola vez a
        //getListUsersUseCase("users")

        assert(usersViewModel.listUsersMLD.value == listUserModelTest) // verificamos si el valor enviado en el live dada con postvalue
        // es igual a listUserModelTest que configuramos en el proceso del mock
        assert(usersViewModel.isLoading.value == false)

    }
}

