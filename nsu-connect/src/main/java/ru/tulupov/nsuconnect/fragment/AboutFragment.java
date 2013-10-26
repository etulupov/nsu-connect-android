package ru.tulupov.nsuconnect.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.codechimp.apprater.AppRater;

import java.io.File;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.helper.BitmapHelper;
import ru.tulupov.nsuconnect.helper.IntentActionHelper;
import ru.tulupov.nsuconnect.images.ImageCacheManager;
import ru.tulupov.nsuconnect.service.DataService;


public class AboutFragment extends BaseFragment {


    public static AboutFragment newInstance(final Context context) {
        return (AboutFragment) Fragment.instantiate(context, AboutFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRater.rateNow(getActivity());
            }
        });
    }

    @Override
    public int getTitleId() {
        return R.string.about_title;
    }

    @Override
    public int getMenuItemId() {
        return R.id.menu_about_application;
    }
}
