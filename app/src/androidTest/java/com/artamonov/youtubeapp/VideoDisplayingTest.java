package com.artamonov.youtubeapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.artamonov.youtubeapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class VideoDisplayingTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkDisplayingVideos() {
        onView(withId(R.id.search_input)).perform(typeText("Foo"), pressImeActionButton());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getRVSize() > 0) {
            onView(withId(R.id.videos_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

    private int getRVSize() {
        RecyclerView recyclerView = activityActivityTestRule.getActivity().findViewById(R.id.videos_recycler_view);
        return recyclerView.getAdapter().getItemCount();
    }
}
