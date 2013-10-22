package ru.tulupov.nsuconnect.util;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;


public class OrmLoader<D> extends AsyncTaskLoader<D> {
    public OrmLoader(Context context) {
        super(context);
    }

    @Override
    public D loadInBackground() {
        return null;
    }


}
