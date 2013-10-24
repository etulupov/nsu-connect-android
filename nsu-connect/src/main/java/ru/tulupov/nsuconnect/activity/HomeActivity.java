package ru.tulupov.nsuconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.fragment.MessagesFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;


public class HomeActivity extends BaseSlidingMenuActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(MessagesFragment.newInstance(getApplicationContext()));
    }


}