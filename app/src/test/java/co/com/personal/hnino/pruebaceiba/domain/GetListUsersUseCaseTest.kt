package co.com.personal.hnino.pruebaceiba.domain

import co.com.personal.hnino.pruebaceiba.data.DataRepository
import co.com.personal.hnino.pruebaceiba.domain.model.AddressModel
import co.com.personal.hnino.pruebaceiba.domain.model.CompanyModel
import co.com.personal.hnino.pruebaceiba.domain.model.GeoModel
import co.com.personal.hnino.pruebaceiba.domain.model.UsersModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetListUsersUseCaseTest{   // aqui podemos lanzar todos los test al mismo tiempo

    @RelaxedMockK // Si no preparamos y definimos una respuesta mockeada de los metodos de la clase que  vamos a Testiar,
    //es decir de la clase DataRepository que declaramos mas adelante, entonces esta etiqueta nos ayuda a generar
    // esa respuesta automaticamente y el test funciona normalmente.

    //@MockK // Si no preparamos y definimos una respuesta mockeadaa de los metodos de la clase que  vamos a Testiar,
    // es decir de la clase DataRepository que declaramos mas adelante, entonces esta etiqueta y/o anotacion intentara
    // acceder a un metodo que no hemos preparado y por tanto el test falla, Esta anotación No nos ayuda a generar esa
    // respuesta automaticamente como si lo hace @RelaxedMockK. Lo mejor es usar la anotación @MockK, debido a
    // que tenemos control total de los test, pero en este ejemplo vamos a usar @RelaxedMockK para fascilitar el proceso.

    private lateinit var repository: DataRepository // estamos declarando la variable repository de tipo DataRepository
    // y sin instanciarla, pero de ello se va a encargar mock al configurarlo con la etiqueta @RelaxedMockK

    lateinit var getListUsersUseCase: GetListUsersUseCase // declaramos la variable getListUsersUseCase de tipo GetListUsersUseCase ya que
    //es la clase que vamos a testear y la instanceamos mas adelante.

    @Before
    fun onBefore(){
        //el codigo existente en esta función se va a ejecutar antes de los test debido a que esta
        // funcion esta configurada con la etiqueta @Before de la libreria junit.
        MockKAnnotations.init(this) // inicializamos la libreria de Mock para mockear el repositorio en cuestion

        getListUsersUseCase = GetListUsersUseCase(repository) // cremos una instancia de la clase que queremos testear
    }

    @Test // Anotación para configurar que la siguiente función es un Test
    fun `when The DataBase Is Empty Then Get Values From Api And save To Database`() = runBlocking {
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runBlocking nos ayuda a lanzar corrutinas

        //Given. Es la respuesta del repositorio mockeado que vamos a dar o a entregar
        coEvery { repository.getlistUsersFromDataBase() } returns emptyList<UsersModel>()
        //Estamos mockeando el repositorio para que cuando se llame la funcion repository.getlistUsersFromDataBase(),
        // nos retorne una lista vacia.

        // Ojo... Si la implementación del metodo .getlistUsersFromDataBase() no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coEvery{} deberiamos poner Every. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.

        // Como ya contamos con una respuesta mockeada para el caso del metodo repository.getlistUsersFromDataBase() entoces ya podriamos
        // usar la anotación @MockK , pero unicamente para este metodo. ya que si se intentara usar para acceder a un metodo que no hemos
        // preparado, entonces el test falla.


        //When..... El given, lo damos cuando llamemos al caso de uso getListUsersUseCase("users")
        getListUsersUseCase("users")

        //then ..... lo que tiene que pasar cuando llamemos al caso de uso getListUsersUseCase
        coVerify(exactly = 1) { repository.getListaUsersFromApi("users")} // El test verifica que se esta llamando una sola vez a
        //repository.getListaUsersFromApi("users")

        coVerify(exactly = 2) { repository.getlistUsersFromDataBase() } // El test verifica que se esta llamando dos veces  a
        //repository.getlistUsersFromDataBase()

        coVerify(exactly = 0) { repository.getListaPostsUsersFromApi(any()) } // El test verifica que no se llame ninguna vez  a
        // repository.getListaPostsUsersFromApi(any()

        // Ojo... Si la implementación del metodo .getlistUsersFromDataBase() no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coVerify{} deberiamos poner Verify. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.
    }

    @Test
    fun `when The DataBase Is Not Empty Then Get Values From Database`() = runBlocking {
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runBlocking nos ayuda a lanzar corrutinas

        //Given =>
        val listUserModelTest = listOf(
            UsersModel(1, "Henry Francisco Niño", "hnino","henrybmx@gmail.com",
            AddressModel("cll 72","suite 1","Bogota D.C.", "110021", GeoModel("1111111","22222222")),
            "3103354642","www.website1.com", CompanyModel("Company1 SAS","catchPhrase 1", "bs 1")),

            UsersModel(2, "Cesar Alejandro Niño", "ceang","cesaralejo@gmail.com",
            AddressModel("cll 73","suite 2","Garagoa D.C.", "150021", GeoModel("3333333","44444444")),
            "3118571599","www.website2.com", CompanyModel("Company2 SAS","catchPhrase 2", "bs 2"))
            )

        coEvery { repository.getlistUsersFromDataBase() } returns listUserModelTest

        //when =>
        val response = getListUsersUseCase("users")

        //then =>
        //then ..... lo que tiene que pasar cuando llamemos al caso de uso getListUsersUseCase
        coVerify(exactly = 0) { repository.getListaUsersFromApi("users")} // El test verifica que no se llame ninguna vez a
        //repository.getListaUsersFromApi("users")

        coVerify(exactly = 1) { repository.getlistUsersFromDataBase() } // El test verifica que se esta llamando una sola vez a
        //repository.getlistUsersFromDataBase()

        coVerify(exactly = 0) { repository.getListaPostsUsersFromApi(any()) } // El test verifica que no se llame ninguna vez a
        // repository.getListaPostsUsersFromApi(any()

        assert(listUserModelTest == response) // assert() comprueba que el codigo dentro genere un true

        assert(response?.size == 2)
    }
}