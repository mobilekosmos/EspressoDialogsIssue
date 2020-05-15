package com.issues.espressoidling

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Assert
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivity2Test {

    private lateinit var mIdlingResource: IdlingResource

//    @Test
    fun proveEspressoDialogsIssue() {
        ActivityScenario.launch(MainActivity2::class.java).use({ scenario ->
            Log.d("+++", "state: " + scenario.getState())
            scenario.onActivity(ActivityAction<MainActivity2> { activity ->
                mIdlingResource = activity.getIdlingResource()
                // To prove that the test fails, omit this call:
                //                    IdlingRegistry.getInstance().register(mIdlingResource);
            })
            Assert.assertTrue(scenario.getState() == Lifecycle.State.RESUMED)
            Log.d("+++", "0")
            IdlingRegistry.getInstance().register(mIdlingResource)
            Log.d("+++", "1")
            // TOFIX: Espresso doesn't find anything and we get the timeout exception.
            Espresso.onView(withText("OK")).inRoot(isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Log.d("+++", "2")
        })
    }

    @Test
    fun proveEspressoIntendedIssue0() {
        Intents.init()
        ActivityScenario.launch(MainActivity2::class.java).use({ scenario ->
            Log.d("+++", "state: " + scenario.getState())
            Espresso.closeSoftKeyboard()
            intended(
                hasComponent(
                    MainActivity::class.java.getName()
                )
            )
            Log.d("+++", "0")
        })
        Intents.release()
        for (x in 0..30) {
            proveEspressoIntendedIssue1()
        }
    }

    @Test
    fun proveEspressoIntendedIssue1() {
        Intents.init()
        ActivityScenario.launch(MainActivity2::class.java).use({ scenario ->
            Log.d("+++", "state: " + scenario.getState())
            intended(
                hasComponent(
                    MainActivity::class.java.getName()
                )
            )
            Log.d("+++", "0")
        })
        Intents.release()
    }

    @After
    fun unregisterIdlingResource() {
        Log.d("+++", "After: unregisterIdlingResource")
//        IdlingRegistry.getInstance().unregister(mIdlingResource)
//        Intents.release()
    }
}