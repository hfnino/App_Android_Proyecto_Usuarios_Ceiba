package co.com.personal.hnino.pruebaceiba.ui.view

import androidx.activity.viewModels
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.ui.viewmodel.UsersViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class) //usamos esta anotación para que en la clase ListUsersActivityTest
// se pueda usar android junit class runner
class ListUsersActivityTest{

    //creamos una regla vinculada a la actividad ListUsersActivity
    @get:Rule
    var activityTestScenarioRule = ActivityScenarioRule(ListUsersActivity::class.java) //creamos la variable activityTestScenarioRule
    // y la instanciamos para crear un objeto asociado a la actividad que vamos a testear

    @Test
    fun whenTextIsEnteredToSearchForUserThatDoesNotExist() {
        // Cuando se ejecuta este test, en el campo de texto TextInputEditText con id tietToQuery1 vamos a escribir el texto
        //"Henry Francisco Niño García" (No existe en la data descargada de la APi)
        Thread.sleep(7000)
        onView(withId(R.id.tietToQuery1)) // hacemos referencia al campo TextInputEditText con id tietToQuery1
            .perform(typeText("Henry Francisco Nino"), closeSoftKeyboard()) // accion que se va a realizar
        // EL metodo closeSoftKeyboard() hace parte de la libreria androidx.test.espresso.action.ViewActions.y nos ayuda a cerrar el teclado
        Thread.sleep(2000)
    }

    @Test
    fun whenTextIsEnteredToSearchForUserThatDoesExist(){
        // Cuando se ejecuta este test, en el campo de texto TextInputEditText con id tietToQuery1 vamos a escribir el texto
        //"Leanne Graham" (Si existe en la data descargada de la APi)
        Thread.sleep(7000)
        onView(withId(R.id.tietToQuery1))  // hacemos referencia al campo TextInputEditText con id tietToQuery1
            .perform(typeText("Leanne Graham"), closeSoftKeyboard()) // accion que se va a realizar
        // EL metodo closeSoftKeyboard() hace parte de la libreria androidx.test.espresso.action.ViewActions.y nos ayuda a cerrar el teclado
        Thread.sleep(2000)
    }
}