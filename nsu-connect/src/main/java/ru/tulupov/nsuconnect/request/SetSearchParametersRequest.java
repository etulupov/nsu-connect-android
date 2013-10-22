package ru.tulupov.nsuconnect.request;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.tulupov.nsuconnect.model.Online;
import ru.tulupov.nsuconnect.model.SearchParameters;
import ru.tulupov.nsuconnect.model.Session;
import ru.tulupov.nsuconnect.model.Success;

public class SetSearchParametersRequest extends BaseRequest<Success> {
    private static final String PARAMS_GENDER = "gender";
    private static final String PARAMS_AGE = "age";
    private static final String PARAMS_SEARCH_GENDER = "search_gender";
    private static final String PARAMS_SEARCH_AGE = "search_age";
    private static final String PARAMS_CITY_ID = "city_id";
    private static final String PARAMS_NSU_DEPARTMENT = "nsu_department";
    private static final String PARAMS_SEARCH_NSU_DEPARTMENT = "search_nsu_department";
    private static final String PARAMS_PROVIDER = "provider";

    private static final String SET_COOKIE_KEY = "Set-Cookie";

    private SearchParameters searchParameters;

    public SetSearchParametersRequest(Session session, SearchParameters searchParameters, Response.Listener<Success> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Config.AJAX_ENDPOINT, session, Success.class, listener, errorListener);
        this.searchParameters = searchParameters;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();

        params.put(Constants.ACTION, Constants.ACTION_SET_SETTINGS);
        params.put(PARAMS_GENDER, encodeYourGender(searchParameters.getYourGender()));
        params.put(PARAMS_AGE, encodeYourAge(searchParameters.getYourAge()));
        params.put(PARAMS_SEARCH_GENDER, encodeTargetGender(searchParameters.getTargetGender()));
        params.put(PARAMS_SEARCH_AGE, encodeTargetAge(searchParameters.getTargetAge()));
        params.put(PARAMS_CITY_ID, "0");
        params.put(PARAMS_NSU_DEPARTMENT, "0");
        params.put(PARAMS_SEARCH_NSU_DEPARTMENT, "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        params.put(PARAMS_PROVIDER, "nsu");

        return params;
    }

    private String encodeYourGender(List<Integer> list) {
        if (!list.isEmpty()) {
            return String.valueOf(list.get(0));
        }
        return "0";
    }

    private String encodeTargetGender(List<Integer> list) {
        List<String> flags = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            flags.add("0");
        }

        for (Integer position : list) {
            flags.set(position, "1");
        }

        return TextUtils.join(",", flags);
    }

    private String encodeYourAge(List<Integer> list) {
        if (!list.isEmpty()) {
            return String.valueOf(list.get(0));
        }
        return "0";
    }

    private String encodeTargetAge(List<Integer> list) {
        List<String> flags = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            flags.add("0");
        }

        for (Integer position : list) {
            flags.set(position, "1");
        }

        return TextUtils.join(",", flags);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Map<String, String> headers = response.headers;

        if (headers.containsKey(SET_COOKIE_KEY)) {
            session.setSearch(headers.get(SET_COOKIE_KEY));
        }

        return super.parseNetworkResponse(response);
    }
}
