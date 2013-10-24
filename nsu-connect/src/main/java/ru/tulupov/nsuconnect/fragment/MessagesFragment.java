package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.tulupov.nsuconnect.R;


public class MessagesFragment extends BaseFragment {


    public static MessagesFragment newInstance(final Context context) {
        return (MessagesFragment) Fragment.instantiate(context, MessagesFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_welcome, container, false);
    }

    @Override
    public int getTitleId() {
        return R.string.conversations_title;
    }
    @Override
    public int getMenuItemId() {
        return R.id.menu_messages;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fgt_messages, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
