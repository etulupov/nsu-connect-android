package ru.tulupov.nsuconnect.fragment;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.util.adapter.AdapterLoaderCallback;
import ru.tulupov.nsuconnect.util.adapter.BeanHolderAdapter;

public abstract class LoaderListFragment<T> extends BaseFragment {
    private static final int UPDATE_LIST_LOADER_ID = 0;

    private BeanHolderAdapter<T, ?> adapter;
    private ListView list;


    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (getActivity() != null) {
                update();
                onDataChange();
            }
        }
    };


    protected void onDataChange() {

    }

    protected void onItemClick(int position, T item) {

    }

    protected boolean onItemLongPress(int position, T item) {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        list = (ListView) view.findViewById(R.id.list);
        adapter = getAdapter();
        list.setAdapter(adapter);

    }




    public void update() {

        LoaderManager loaderManager = getLoaderManager();
        Loader loader = loaderManager.getLoader(UPDATE_LIST_LOADER_ID);
        if (loader == null) {

            loaderManager.initLoader(UPDATE_LIST_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<T>>() {
                @Override
                public Loader<List<T>> onCreateLoader(int i, Bundle bundle) {
                    return LoaderListFragment.this.onCreateLoader();
                }

                @Override
                public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
                    adapter.updateList(data);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            LoaderListFragment.this.onItemClick(position, adapter.getItem(position));
                        }
                    });
                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                            return LoaderListFragment.this.onItemLongPress(position, adapter.getItem(position));

                        }
                    });
                    LoaderListFragment.this.onLoadFinished();
                }

                @Override
                public void onLoaderReset(Loader<List<T>> loader) {
                    list.setAdapter(null);
                }
            });

        }
        loader = loaderManager.getLoader(UPDATE_LIST_LOADER_ID);
        loader.forceLoad();
    }

    protected void onLoadFinished() {

    }

    protected abstract Loader<List<T>> onCreateLoader();

    protected abstract Uri getContentUri();

    protected abstract BeanHolderAdapter<T, ?> getAdapter();

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getContentResolver().registerContentObserver(getContentUri(), false, contentObserver);
        update();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getContentResolver().unregisterContentObserver(contentObserver);
    }


}
