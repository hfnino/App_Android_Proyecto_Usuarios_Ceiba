package co.com.personal.hnino.pruebaceiba.ui.view

import androidx.activity.viewModels
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import co.com.personal.hnino.pruebaceiba.R
import co.com.personal.hnino.pruebaceiba.ui.viewmodel.UsersViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class) //usamos esta anotación para que en la clase ListUsersActivityTest
// se pueda usar android junit class runner
class InfoUserActivityTest{

    //creamos una regla vinculada a la actividad ListUsersActivity
    @get:Rule
    var activityTestScenarioRule = ActivityScenarioRule(InfoUserActivity::class.java) //creamos la variable
        // activityTestScenarioRule y la instanciamos para crear un objeto asociado a la actividad que vamos a testear

    @get:Rule
    var intentTestScenarioRule = IntentsTestRule(InfoUserActivity::class.java) //creamos la variable
    // intentTestScenarioRule y la instanciamos para crear un objeto  asociado a la actividad que vamos a testear


    @Test
    fun visualizingdata() {

        onView(withId(R.id.tvNombreUser2)) // hacemos referencia al textview de la vida con id tvNombreUser2
            .perform(typeText("Henry Francisco Niño García")) // seteamos el texto especificado en el
                                                                            //campo con id tvNombreUser2


        onView(withId(R.id.tvNumTelefonico2)) // hacemos referencia al textview de la vida con id tvNumTelefonico2
            .perform(typeText("+57 3103354642")) // seteamos el texto especificado en el
                                                                //campo con id tvNumTelefonico2

        onView(withId(R.id.tvEmail2)) // hacemos referencia al textview de la vida con id tvNombreUser2
            .perform(typeText("+henrybmx@gmail.com")) // seteamos el texto especificado en el
                                                                     //campo con id tvEmail2

        onView(withId(R.id.btnVolver))
            .perform(click()) // la acción a realizar es ha de un click en el botón

    }
}