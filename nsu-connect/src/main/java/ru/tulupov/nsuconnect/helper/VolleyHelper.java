package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;

public class VolleyHelper {
    public static RequestQueue newRequestQueue(Context context) {

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        HttpStack httpStack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));

        Cache cache = new Cache() {
            @Override
            public Entry get(String s) {
                return null;
            }

            @Override
            public void put(String s, Entry entry) {

            }

            @Override
            public void initialize() {

            }

            @Override
            public void invalidate(String s, boolean b) {

            }

            @Override
            public void remove(String s) {

            }

            @Override
            public void clear() {

            }
        };
        Network network = new BasicNetwork(httpStack);
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        return queue;
    }
}
