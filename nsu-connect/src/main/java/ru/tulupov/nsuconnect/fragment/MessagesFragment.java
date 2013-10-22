package ru.tulupov.nsuconnect.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.adapter.MessageAdapter;
import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.loader.MessageLoader;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.util.adapter.AdapterLoaderCallback;


public class MessagesFragment extends Fragment {
    private static final int UPDATE_LIST_LOADER_ID = 0;
    private MessageAdapter adapter;
    private ListView list;
    private View footer;
    private boolean listAtTheEnd;
    private boolean firstTime = true;
    private BroadcastReceiver updateListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            update();

        }
    };

    public static MessagesFragment newInstance(final Context context) {
        return (MessagesFragment) MessagesFragment.instantiate(context, MessagesFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_messsages, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        footer = View.inflate(getActivity(), R.layout.footer_messages, null);

        list = (ListView) view.findViewById(R.id.list);
        list.addFooterView(footer);
        adapter = new MessageAdapter();
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView lw, final int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                listAtTheEnd = (lastItem == totalItemCount);
            }
        });

    }


    private void update() {
        LoaderManager loaderManager = getLoaderManager();
        Loader loader = loaderManager.getLoader(UPDATE_LIST_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(UPDATE_LIST_LOADER_ID, null, new AdapterLoaderCallback<Message>(adapter) {
                @Override
                public Loader<List<Message>> onCreateLoader(int i, Bundle bundle) {
                    return new MessageLoader(getActivity());
                }

                @Override
                public void onLoadFinished(Loader<List<Message>> loader, List<Message> data) {
                    super.onLoadFinished(loader, data);

                    if (listAtTheEnd && !firstTime) {

                        list.setSelection(adapter.getCount() - 1);
                    }

                    if (firstTime) {
                        firstTime = false;
                        list.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                list.setSelection(adapter.getCount() - 1);
                            }
                        }, 1000);
                    }


                }
            });
        }
        loader = loaderManager.getLoader(UPDATE_LIST_LOADER_ID);
        loader.forceLoad();
    }


    @Override
    public void onResume() {
        super.onResume();
        update();
        IntentFilter updateListFilter = new IntentFilter(DatabaseConstants.ACTION_UPDATE_MESSAGE_LIST);
        getActivity().registerReceiver(updateListReceiver, updateListFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(updateListReceiver);
    }
}
