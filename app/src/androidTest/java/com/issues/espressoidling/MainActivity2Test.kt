package com.issues.espressoidling

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.not
import org.junit.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivity2Test {

    private lateinit var mIdlingResource: IdlingResource

    @Test
    fun useAppContext() {
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
            Log.d("+++", "3")
            // TOFIX: Espresso doesn't find anything and we get the timeout exception.
            Espresso.onView(withText("OK")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Log.d("+++", "4")
        })
    }

    @After
    fun unregisterIdlingResource() {
        Log.d("+++", "After: unregisterIdlingResource")
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }
}