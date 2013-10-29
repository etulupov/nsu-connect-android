package ru.tulupov.nsuconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.codechimp.apprater.AppRater;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.fragment.MessagesFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;
import ru.tulupov.nsuconnect.helper.ChatHelper;


public class HomeActivity extends BaseSlidingMenuActivity {
    public static final String EXTRA_NEW_CHAT = "new_chat";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        showFragment(MessagesFragment.newInstance(getApplicationContext()));
        if (getIntent().getBooleanExtra(EXTRA_NEW_CHAT, false)) {
            ChatHelper.openChatFragment(this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AppRater.app_launched(this);
    }
}