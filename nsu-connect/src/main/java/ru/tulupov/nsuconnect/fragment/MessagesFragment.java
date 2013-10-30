package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.adapter.MessagesAdapter;
import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.database.loader.ChatLoader;
import ru.tulupov.nsuconnect.helper.ChatHelper;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.service.DataService;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;


public class MessagesFragment extends LoaderListFragment<Chat> {


    private static final String TAG = MessagesFragment.class.getSimpleName();

    public static MessagesFragment newInstance(final Context context) {
        return (MessagesFragment) Fragment.instantiate(context, MessagesFragment.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_messages, container, false);
    }

    @Override
    protected void onItemClick(int position, Chat item) {
        addFragment(ChatFragment.newInstance(getActivity(), item.getId()));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_conversation:
                ChatHelper.openChatFragment(this);
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "menu_new_conversation", null).build());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Loader<List<Chat>> onCreateLoader() {
        return new ChatLoader(getActivity());
    }

    @Override
    protected Uri getContentUri() {
        return ContentUriHelper.getChatUri();
    }

    @Override
    protected BeanHolderAdapter<Chat, ?> getAdapter() {
        MessagesAdapter adapter = new MessagesAdapter();
        adapter.setOnClickListener(new MessagesAdapter.OnClickListener() {
            @Override
            public void onStop(Chat chat) {
                chat.setActive(false);
                try {
                    getActivity().startService(new Intent(getActivity(), DataService.class)
                            .setAction(DataService.ACTION_DESTROY_SESSION).putExtra(DataService.EXTRA_ID, chat.getId()));

                    HelperFactory.getHelper().getChatDao().deactivateChat(chat.getId());
                    ContentUriHelper.notifyChange(getActivity(), ContentUriHelper.getChatUri());


                } catch (SQLException e) {
                    Log.e(TAG, "error", e);
                }
            }
        });
        return adapter;
    }

    @Override
    protected boolean onItemLongPress(int position, final Chat item) {
        if (item.isActive()) {
            return false;
        }

        DialogItemList dialogItemList = DialogItemList.newInstance(getActivity(), R.array.messages_chat_actions);

        dialogItemList.setOnItemClickListener(new DialogItemList.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        try {

                            HelperFactory.getHelper().getChatDao().delete(item);
                            ContentUriHelper.notifyChange(getActivity(), ContentUriHelper.getChatUri());

                        } catch (SQLException e) {
                            Log.e(TAG, "error", e);
                        }
                        return;
                }
            }
        });
        showDialog(dialogItemList);
        return true;
    }
}
