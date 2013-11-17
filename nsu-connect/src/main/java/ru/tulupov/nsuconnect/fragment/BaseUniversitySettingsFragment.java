package ru.tulupov.nsuconnect.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.helper.VolleyHelper;
import ru.tulupov.nsuconnect.model.Provider;
import ru.tulupov.nsuconnect.model.ProviderResult;
import ru.tulupov.nsuconnect.request.GetProviderRequest;


public abstract class BaseUniversitySettingsFragment extends BaseSearchSettingsFragment {
    private static final long DELAY = 1000;

    private List<String> strings = new ArrayList<String>();
    private List<Integer> ids = new ArrayList<Integer>();
    private RequestQueue requestQueue;
    private Handler handler = new Handler();
    private boolean once;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_search_settings_university, container, false);
    }


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


    private Runnable loadListRunnable = new Runnable() {
        @Override
        public void run() {
            requestQueue.add(new GetProviderRequest(new Response.Listener<ProviderResult>() {
                @Override
                public void onResponse(ProviderResult response) {
                    strings.clear();
                    ids.clear();

                    preprocessUniversitiesList(strings, ids);

                    if (getView() != null) {
                        getView().findViewById(R.id.container).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    }


                    if (response != null) {
                        for (Provider provider : response.getProviders()) {
                            strings.add(provider.getName());
                            ids.add(provider.getId());
                        }
                    }

                    BaseUniversitySettingsFragment.super.initAdapter();
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

    protected void preprocessUniversitiesList(List<String> strings, List<Integer> ids) {

    }

    @Override
    protected void initAdapter() {
        handler.post(loadListRunnable);

    }


    @Override
    protected int getItemsArrayId() {
        return 0;
    }


    @Override
    public String[] getItems() {
        return strings.toArray(new String[strings.size()]);
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_SINGLE;
    }


    public List<Integer> getSelectedItemIds() {
        List<Integer> list = new ArrayList<Integer>();
        for (int position : getSelectedItems()) {
            list.add(ids.get(position));
        }
        return list;
    }
}
