package com.wildlearner

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.wildlearner.presentation.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// to run this test you must give some permissions to app
// app info -> other permissions -> (tick all permissions)

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun clickForAddData() {
        onView(withId(R.id.persistentSearchView)).perform(click())
        onView(withId(R.id.persistentSearchView)).check(matches(hasFocus()))
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.persistentSearchView))))
            .perform(typeText("Hamed Sharifi"))
        onView(isRoot()).perform(waitFor(3000)) // to sure soft keyboard is up
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.persistentSearchView))))
            .perform(ViewActions.pressImeActionButton ())
        onView(isRoot()).perform(waitFor(7000)) // to sure web service results arrived
        onView(withId(R.id.searchList)).check(matches(atPosition(0, isDisplayed())))
        onView(withId(R.id.searchList)).check(matches(atPosition(1, isDisplayed())))
    }

    private fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(
            RecyclerView::class.java
        ) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    @Test
    fun check_recyclerview_is_displayed() {
        onView(withId(R.id.searchList)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {

    }


}