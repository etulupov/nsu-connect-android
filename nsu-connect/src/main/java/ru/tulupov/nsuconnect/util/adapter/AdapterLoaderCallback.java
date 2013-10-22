package ru.tulupov.nsuconnect.util.adapter;


import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.CursorAdapter;

import java.util.List;


public abstract class AdapterLoaderCallback<T> implements LoaderManager.LoaderCallbacks<List<T>> {
    protected BeanHolderAdapter<T, ?> adapter;

    public AdapterLoaderCallback(BeanHolderAdapter<T, ?> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
        adapter.updateList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<T>> loader) {
        adapter.updateList(null);
    }
}