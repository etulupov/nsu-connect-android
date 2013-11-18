package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;

import ru.tulupov.nsuconnect.util.Log;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ListView;

import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.adapter.MessagesAdapter;
import ru.tulupov.nsuconnect.database.ContentUriHelper;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.database.loader.ChatLoader;
import ru.tulupov.nsuconnect.helper.ChatHelper;
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

    private MessagesAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        adapter = new MessagesAdapter();
        adapter.setOnClickListener(new MessagesAdapter.OnClickListener() {
            @Override
            public void onStop(final Chat chat) {
                chat.setActive(false);
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "exit_chat_item", null).build());


                deactivateChat(chat);


            }

            @Override
            public void onChecked(int position) {

                getListView().setItemChecked(position, true);
                adapter.notifyDataSetChanged();
            }
        });

        super.onViewCreated(view, savedInstanceState);
        getListView().setEmptyView(findViewById(R.id.empty));


        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        getListView(),
                        new SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, final int[] reverseSortedPositions) {
                                DialogConfirmDeletionFragment dialog = DialogConfirmDeletionFragment.newInstance(getActivity());
                                dialog.setOnDeleteListener(new DialogConfirmDeletionFragment.OnDeleteListener() {
                                    @Override
                                    public void onDelete() {
                                        EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "delete_chat_yes", null).build());


                                        try {
                                            List<Chat> ids = new ArrayList<Chat>();



                                            for (int position : reverseSortedPositions) {


                                                Chat chat = adapter.getItem((int) position);

                                                if (chat.isActive()) {
                                                    getActivity().startService(new Intent(getActivity(), DataService.class)
                                                            .setAction(DataService.ACTION_DESTROY_SESSION).putExtra(DataService.EXTRA_ID, chat.getId()));

                                                    HelperFactory.getHelper().getChatDao().deactivateChat(chat.getId());
                                                }

                                                ids.add(adapter.getItem((int) position));

                                            }


                                            HelperFactory.getHelper().getChatDao().delete(ids);
                                            ContentUriHelper.notifyChange(getActivity(), ContentUriHelper.getChatUri());
                                        } catch (SQLException e) {
                                            Log.e(TAG, "error removing chat", e);
                                        }

                                    }
                                });
                                showDialog(dialog);
                            }
                        });
        getListView().setOnTouchListener(touchListener);

        getListView().setOnScrollListener(touchListener.makeScrollListener());


    }

    @Override
    protected void onItemClick(int position, Chat item) {
        if (!isAction) {
            addFragment(ChatFragment.newInstance(getActivity(), item.getId(), item.isActive()));
        } else {
            SparseBooleanArray checked = getListView().getCheckedItemPositions();
            boolean hasCheckedElement = false;
            for (int i = 0; i < checked.size() && !hasCheckedElement; i++) {
                hasCheckedElement = checked.valueAt(i);
            }
            if (!hasCheckedElement) {
                isAction = false;
                updateAction();
            }
        }
    }

    @Override
    public int getTitleId() {
        return R.string.conversations_title;
    }

    @Override
    public int getMenuItemId() {
        return R.id.menu_messages;
    }

    private boolean isAction;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (isAction) {
            inflater.inflate(R.menu.fgt_messages_action, menu);
        } else {
            inflater.inflate(R.menu.fgt_messages, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_conversation:
                ChatHelper.openChatFragment(this);
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "menu_new_conversation", null).build());
                break;

            case R.id.menu_delete:
                removeChats();
                break;
            case R.id.menu_select_all:
                toggleSelectAll();
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

        return adapter;
    }

    private void deactivateChat(final Chat chat) {
        DialogConfirmExitFragment dialog = DialogConfirmExitFragment.newInstance(getActivity());
        dialog.setOnExitListener(new DialogConfirmExitFragment.OnExitListener() {
            @Override
            public void onExit() {
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "exit_chat_yes", null).build());
                try {
                    getActivity().startService(new Intent(getActivity(), DataService.class)
                            .setAction(DataService.ACTION_DESTROY_SESSION).putExtra(DataService.EXTRA_ID, chat.getId()));

                    HelperFactory.getHelper().getChatDao().deactivateChat(chat.getId());
                    ContentUriHelper.notifyChange(getActivity(), ContentUriHelper.getChatUri());


                } catch (SQLException e) {
                    Log.e(TAG, "error deactivating chat", e);
                }
            }
        });
        showDialog(dialog);
    }

    private void removeChats() {
        DialogConfirmDeletionFragment dialog = DialogConfirmDeletionFragment.newInstance(getActivity());
        dialog.setOnDeleteListener(new DialogConfirmDeletionFragment.OnDeleteListener() {
            @Override
            public void onDelete() {
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "delete_chat_yes", null).build());


                try {
                    List<Chat> ids = new ArrayList<Chat>();

                    SparseBooleanArray checked = getListView().getCheckedItemPositions();

                    for (int i = 0; i < checked.size(); i++) {
                        int position = checked.keyAt(i);
                        if (checked.valueAt(i)) {
                            Chat chat = adapter.getItem((int) position);

                            if (chat.isActive()) {
                                getActivity().startService(new Intent(getActivity(), DataService.class)
                                        .setAction(DataService.ACTION_DESTROY_SESSION).putExtra(DataService.EXTRA_ID, chat.getId()));

                                HelperFactory.getHelper().getChatDao().deactivateChat(chat.getId());
                            }

                            ids.add(adapter.getItem((int) position));
                        }
                    }


                    HelperFactory.getHelper().getChatDao().delete(ids);
                    ContentUriHelper.notifyChange(getActivity(), ContentUriHelper.getChatUri());
                } catch (SQLException e) {
                    Log.e(TAG, "error removing chat", e);
                }
                isAction = false;
                updateAction();
            }
        });
        showDialog(dialog);
    }

    private void clearCheckedItems() {
        for (int i = 0; i < getListView().getCount(); i++) {
            if (getListView().getChildAt(i) instanceof Checkable) {
                ((Checkable) getListView().getChildAt(i)).setChecked(false);
            }
            getListView().setItemChecked(i, false);
        }
        for (int i = 0; i < getListView().getChildCount(); i++) {
            if (getListView().getChildAt(i) instanceof Checkable) {
                ((Checkable) getListView().getChildAt(i)).setChecked(false);
            }
        }
    }

    private boolean isAllSelected;

    private void toggleSelectAll() {
        isAllSelected = !isAllSelected;
        for (int i = 0; i < getListView().getCount(); i++) {

            getListView().setItemChecked(i, isAllSelected);
        }
    }

    private void updateAction() {

        if (isAction) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        } else {

            clearCheckedItems();

            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        }

        isAllSelected = false;
//        adapter.setCheckable(isAction);
//        adapter.notifyDataSetChanged();
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    protected boolean onItemLongPress(int position, final Chat item) {
        isAction = !isAction;
        updateAction();
        if (isAction) {
            getListView().setItemChecked(position, true);
        }

//        SparseBooleanArray checked = getListView().getCheckedItemPositions();
//        boolean hasCheckedElement = false;
//        for (int i = 0; i < checked.size() && !hasCheckedElement; i++) {
//            hasCheckedElement = checked.valueAt(i);
//        }


//
//        if (item.isActive()) {
//            return false;
//        }
//
//        DialogItemListFragment dialogItemList = DialogItemListFragment.newInstance(getActivity(), R.array.messages_chat_actions);
//
//        dialogItemList.setOnItemClickListener(new DialogItemListFragment.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                switch (position) {
//                    case 0:
//                        EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "messages", "delete_chat", null).build());
//
//                        removeChat(item);
//                        return;
//                }
//            }
//        });
//        showDialog(dialogItemList);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (isAction) {
            isAction = !isAction;
            updateAction();
            return true;
        }
        return false;
    }
}
