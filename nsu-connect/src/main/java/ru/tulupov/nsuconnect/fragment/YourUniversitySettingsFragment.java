package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.helper.VolleyHelper;
import ru.tulupov.nsuconnect.model.Provider;
import ru.tulupov.nsuconnect.model.ProviderResult;
import ru.tulupov.nsuconnect.request.GetProviderRequest;


public class YourUniversitySettingsFragment extends SearchSettingsFragment {
    private static final long DELAY = 2000;

    public static YourUniversitySettingsFragment newInstance(final Context context) {
        return (YourUniversitySettingsFragment) Fragment.instantiate(context, YourUniversitySettingsFragment.class.getName());
    }

    private RequestQueue requestQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = VolleyHelper.newRequestQueue(getActivity());
    }

    @Override
    public void onDestroy() {
        requestQueue.stop();
        handler.removeCallbacks(loadListRunnable);
        super.onDestroy();
    }

    private Handler handler = new Handler();
    private boolean once;

    private Runnable loadListRunnable = new Runnable() {
        @Override
        public void run() {
            requestQueue.add(new GetProviderRequest(new Response.Listener<ProviderResult>() {
                @Override
                public void onResponse(ProviderResult response) {

                    strings.addAll(Arrays.asList(getResources().getStringArray(R.array.search_your_university)));
                    for (int id : getResources().getIntArray(R.array.search_your_university_ids)) {
                        ids.add(id);
                    }


                    if (response != null) {
                        for (Provider provider : response.getProviders()) {
                            strings.add(provider.getName());
                            ids.add(provider.getId());
                        }
                    }

                    YourUniversitySettingsFragment.super.initAdapter();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (!once) {
                        once = true;
                        showToast(R.string.search_university_error);
                    }
                    handler.postDelayed(loadListRunnable, DELAY);
                }
            }
            ));
        }
    };

    @Override
    protected void initAdapter() {
        handler.post(loadListRunnable);

    }

    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_your_university;
    }

    @Override
    protected int getItemsArrayId() {
        return 0;
    }

    private List<String> strings = new ArrayList<String>();
    private List<Integer> ids = new ArrayList<Integer>();

    @Override
    public String[] getItems() {
        return strings.toArray(new String[strings.size()]);
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_SINGLE;
    }

    @Override
    public List<Integer> getSelectedItemIds() {
        return ids;
    }
}
