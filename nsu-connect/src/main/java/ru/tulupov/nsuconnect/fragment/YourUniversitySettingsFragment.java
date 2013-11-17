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


public class YourUniversitySettingsFragment extends BaseUniversitySettingsFragment {


    public static YourUniversitySettingsFragment newInstance(final Context context) {
        return (YourUniversitySettingsFragment) Fragment.instantiate(context, YourUniversitySettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_your_university;
    }

    @Override
    protected void preprocessUniversitiesList(List<String> strings, List<Integer> ids) {
        strings.addAll(Arrays.asList(getResources().getStringArray(R.array.search_your_university)));
        for (int id : getResources().getIntArray(R.array.search_your_university_ids)) {
            ids.add(id);
        }
    }
}
