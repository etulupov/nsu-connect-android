package ru.tulupov.nsuconnect.activity;

import android.os.Bundle;

import org.codechimp.apprater.AppRater;

import ru.tulupov.nsuconnect.fragment.MessagesFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;
import ru.tulupov.nsuconnect.helper.ChatHelper;


public class WelcomeActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        showFragment(WelcomeFragment.newInstance(getApplicationContext()));


    }


}